package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;

    private final UserMapper mapper;


    public void registerUser(@Valid UserRequest request) {
        var encodedDetails = mapper.toUserWithEncodedPassword(request);

        repository.save(encodedDetails);
    }

    public Optional<UserResponse> findByEmail(String email){
        var user = repository.findByEmail(email);
        return Optional.ofNullable(mapper.toUserResponse(user));
    }
}
