package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.dto.ResponseDTO;
import com.lurdharry.authorization.user.UserRequest;
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
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register (
            @RequestBody @Valid UserRequest request
    ){
          service.registerUser(request);

          ResponseDTO response = new ResponseDTO(HttpStatus.OK,"User registered successfully.");

          return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
