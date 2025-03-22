package com.lurdharry.authorization.services;

import com.lurdharry.authorization.exception.EmailAlreadyExistsException;
import com.lurdharry.authorization.exception.ResponseException;
import com.lurdharry.authorization.model.RoleEntity;
import com.lurdharry.authorization.model.User;
import com.lurdharry.authorization.repositories.RoleEntityRepository;
import com.lurdharry.authorization.repositories.UserRepository;
import com.lurdharry.authorization.user.UserMapper;
import com.lurdharry.authorization.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleEntityRepository entityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public User registerUser(UserRequest request){

        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        RoleEntity role =  entityRepository.findByName("USER").orElseThrow(
                () -> new ResponseException("USER role not found", HttpStatus.NOT_FOUND)
        );

        User userToBeAdded =  mapper.toUserWithEncodedPassword(request,role,false);

        return userRepository.save(userToBeAdded);
    }


    // admin only
    public User createAttendant(UserRequest request){

        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        RoleEntity role =  entityRepository.findByName("ATTENDANT").orElseThrow(
                () -> new ResponseException("ATTENDANT role not found", HttpStatus.NOT_FOUND)
        );

        User attendant =  mapper.toUserWithEncodedPassword(request, role,true);

        return userRepository.save(attendant);
    }

//    // admin only
//    public User updateUserRole


}
