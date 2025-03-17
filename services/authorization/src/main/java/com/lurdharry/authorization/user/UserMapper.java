package com.lurdharry.authorization.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder encoder;

    public User toUserWithEncodedPassword (@Valid  UserRequest request){

        return User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .role(request.role())
                .build();
    }

}
