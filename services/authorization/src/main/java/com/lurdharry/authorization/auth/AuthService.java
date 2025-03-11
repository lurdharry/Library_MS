package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.user.UserMapper;
import com.lurdharry.authorization.user.UserRepository;
import com.lurdharry.authorization.user.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;

    private final UserMapper mapper;


    public void registerUser(@Valid UserRequest request) {
        var encodedDetails = mapper.toUserWithEncodedPassword(request);

        repository.save(encodedDetails);
    }
}
