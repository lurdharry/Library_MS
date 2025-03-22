package com.lurdharry.authorization.services;

import com.lurdharry.authorization.exception.EmailAlreadyExistsException;
import com.lurdharry.authorization.model.RoleEntity;
import com.lurdharry.authorization.model.User;
import com.lurdharry.authorization.repositories.RoleEntityRepository;
import com.lurdharry.authorization.repositories.UserRepository;
import com.lurdharry.authorization.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleEntityRepository entityRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRequest request){
        RoleEntity role =  entityRepository.findByName("USER").orElseThrow(
                () -> new EmailAlreadyExistsException()
        )

    }
}
