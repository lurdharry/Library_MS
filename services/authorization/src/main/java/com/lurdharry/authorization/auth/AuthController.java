package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.dto.ResponseDTO;
import com.lurdharry.authorization.user.UserRequest;
import com.lurdharry.authorization.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register (
            @RequestBody @Valid UserRequest request
    ){
          service.registerUser(request);

          ResponseDTO response = ResponseDTO.builder()
                  .status(HttpStatus.OK)
                  .message("User registered successfully.")
                  .build();

          return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(
            @RequestBody @Valid LoginRequest request
    ){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );

        String token = jwtUtil.generateToken(request.email());

        ResponseDTO response = new ResponseDTO(HttpStatus.OK,"Login successful!", Map.of("token",token));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
