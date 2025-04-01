package com.lurdharry.authorization.verification;

import com.lurdharry.authorization.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class VerificationController {

    private final VerificationService service;

    @PostMapping("/initiate")
    public ResponseEntity<ResponseDTO> initiateEmailVerification(@RequestBody @Valid EmailVerifyRequest request){

        service.initiateEmailVerification(request);
        ResponseDTO response = ResponseDTO.builder()
                .message("Verification code sent to your email")
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseDTO> verifyCode(@RequestBody @Valid UpdateStatusRequest request){
        service.verifyEmail(request);

        ResponseDTO response = ResponseDTO.builder()
                .message("Email verified successfully")
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
