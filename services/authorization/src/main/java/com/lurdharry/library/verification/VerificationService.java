package com.lurdharry.library.verification;

import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.user.User;
import com.lurdharry.library.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final EmailService emailService;


    public void initiateEmailVerification(@Valid EmailVerifyRequest request) {
        //first search for the user
        User user=  getUser(request.email());

        // generate token
        final String code = generateCode();
        final LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        // delete existing details
        verificationRepository.findByEmail(request.email()).ifPresentOrElse((res)->{
            res.setToken(code);
            res.setExpiryDate(expiry);
            verificationRepository.save(res);
        },
                () -> {
            EmailVerification tokenDetails = EmailVerification.builder()
                    .email(user.getEmail())
                    .token(code)
                    .expiryDate(expiry)
                    .build();
            verificationRepository.save(tokenDetails);
        });


        // mail the code to the user
        emailService.sendVerificationCode(request.email(), code);
    }


    public void verifyEmail(UpdateStatusRequest request) {

      EmailVerification tokenDetails = verificationRepository.findByEmailAndToken(request.email(),request.code())
              .orElseThrow(()-> new ResponseException("Invalid email and verification code", HttpStatus.NOT_FOUND)
      );

      // check if code has expired
        if (tokenDetails.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new ResponseException("Verification code has expired",HttpStatus.BAD_REQUEST);
        }

      User user = getUser(request.email());
      user.setVerified(true);
      userRepository.save(user);

      verificationRepository.delete(tokenDetails);

    }



    private User getUser(String email) {
        //first search for the user
        return  userRepository.findByEmail(email).orElseThrow(()->
                new ResponseException("User not found", HttpStatus.NOT_FOUND)
        );

    }

    private String generateCode(){
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(900000) + 100000; // Generates a 6-digit number
        return String.valueOf(code);
    }
}
