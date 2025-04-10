package com.lurdharry.library.auth;

import com.lurdharry.library.dto.TokenResponse;
import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.security.CustomUserDetailsService;
import com.lurdharry.library.user.User;
import com.lurdharry.library.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public TokenResponse authorizeLogin(@Valid LoginRequest request){

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(),request.password())
            );

            return buildAccessTokenAndRefreshToken(auth);
        } catch (AuthenticationException e) {

            throw new RuntimeException(e);
        }
    }

    public TokenResponse refreshToken(@Valid RefreshRequest request) {
        String refreshToken = request.refreshToken();
        System.out.println("request.refreshToken()_____"+request.refreshToken());
        Jwt refreshJwt;

        try {
            refreshJwt = jwtDecoder.decode(refreshToken);
            System.out.println("refreshJwt_____"+refreshJwt);
        } catch (JwtException e) {
            System.out.println("e______"+e);
            throw new ResponseException("Invalid token",HttpStatus.UNAUTHORIZED);
        }

        if (!"refresh".equalsIgnoreCase(refreshJwt.getClaim("token_type"))){
            System.out.println("refreshJwt_____"+refreshJwt.getClaim("token_type"));

            throw new ResponseException("Invalid token type",HttpStatus.UNAUTHORIZED);
        }

        // Get the subject email from the refresh token
        String email = refreshJwt.getSubject();

        var userDetails = customUserDetailsService.loadUserByUsername(email);

        System.out.println("customUserDetailsService"+userDetails);
        // Create an authentication object (this example uses the loaded details)
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );

        return buildAccessTokenAndRefreshToken(auth);
    }

    public void updatePassword(UpdatePassRequest request,String email) {
        //check if email exist
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        // check the encoded old password from req against  encoded password in the db
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())){
            throw new ResponseException("Old password is incorrect",HttpStatus.UNAUTHORIZED);
        }

        // encode new password and save it
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);

        // TODO send email notification for successful password change

    }

    // utility function
    private TokenResponse buildAccessTokenAndRefreshToken (Authentication  authentication){
        Instant now = Instant.now();
        Instant accessExpiry = now.plus(Duration.ofMinutes(60));
        Instant refreshExpiry = now.plus(Duration.ofDays(2));

        JwtClaimsSet accessClaim = JwtClaimsSet.builder()
//                .issuer("http://localhost:8070")
                .issuedAt(now)
                .expiresAt(accessExpiry)
                .subject(authentication.getName())
                .claim("roles",authentication.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .build();

        Jwt accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessClaim));

        JwtClaimsSet refreshClaim = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(refreshExpiry)
                .subject(authentication.getName())
                .claim("token_type", "refresh")
                .build();

        Jwt refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshClaim));

        return TokenResponse.builder()
                .accessToken(accessToken.getTokenValue())
                .refreshToken(refreshToken.getTokenValue())
                .expiresIn(String.valueOf(Duration.between(now,accessExpiry).getSeconds()))
                .build();
    }


}
