package com.lurdharry.library.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder encoder;

    public User toUserWithEncodedPassword (@Valid  UserRequest request, RoleEntity role, boolean verified){

        return User.builder()
                .email(request.email())
                .password(encoder.encode(request.password()))
                .firstname(request.firstname())
                .lastname(request.lastname())
                .verified(verified)
                .role(role)
                .build();
    }


    public UserResponse toUserResponse(User user) {

        return  UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .verified(user.isVerified())
                .role(user.getRole().getName())
                .build();
    }
}
