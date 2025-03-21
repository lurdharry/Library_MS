package com.lurdharry.authorization.auth;

import com.lurdharry.authorization.exception.EmailAlreadyExistsException;
import com.lurdharry.authorization.model.User;
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

        if (repository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        var encodedDetails = mapper.toUserWithEncodedPassword(request);

        repository.save(encodedDetails);
    }

    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }
}
