package com.lurdharry.library.auth;

import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.dto.TokenResponse;
import com.lurdharry.library.user.UserRequest;
import com.lurdharry.library.user.UserResponse;
import com.lurdharry.library.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register (
            @RequestBody @Valid UserRequest request
    ){
          service.registerUser(request);

          ResponseDTO response = ResponseDTO.builder()
                  .status(HttpStatus.OK.value())
                  .message("User registered successfully.")
                  .build();

          return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request){

       var response = authService.authorizeLogin(request);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(
            @RequestBody @Valid RefreshRequest request
    ){
        var response = authService.refreshToken(request);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/admin/create-attendant")
    @PreAuthorize("hasAuthority('ADMIN_CREATE_USER')")
    public ResponseEntity<?> createUser( @RequestBody @Valid UserRequest request){
        UserResponse res = service.createAttendant(request);

        ResponseDTO response = ResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Attendant registered successfully.")
                .data(res)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
