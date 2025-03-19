package com.lurdharry.user.user;

import com.lurdharry.user.dto.UserRequest;
import com.lurdharry.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .id(user.getId())
                .role(user.getRole())
                .build();
    }

    public void mergeUser(User user, @Valid UserRequest request) {
        if (isNotBlank(request.firstname())){
            user.setFirstname(request.firstname());
        }
        if (isNotBlank(request.lastname())){
            user.setLastname(request.lastname());
        }
    }
}
