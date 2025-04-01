package com.lurdharry.authorization.user;

import com.lurdharry.authorization.exception.EmailAlreadyExistsException;
import com.lurdharry.authorization.exception.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleEntityRepository entityRepository;
    private final UserMapper mapper;

    public void registerUser(UserRequest request){

        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        RoleEntity role =  entityRepository.findByName(Role.USER).orElseThrow(
                () -> new ResponseException("USER role not found", HttpStatus.NOT_FOUND)
        );

        User userToBeAdded =  mapper.toUserWithEncodedPassword(request,role,false);

        var user = userRepository.save(userToBeAdded);

        mapper.toUserResponse(user);
    }





    // admin only
    public UserResponse createAttendant(UserRequest request){

        if (userRepository.findByEmail(request.email()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exists!");
        }

        RoleEntity role =  entityRepository.findByName(Role.ATTENDANT).orElseThrow(
                () -> new ResponseException(Role.ATTENDANT +" role not found", HttpStatus.NOT_FOUND)
        );

        User attendant =  mapper.toUserWithEncodedPassword(request, role,true);

        var user = userRepository.save(attendant);

        return mapper.toUserResponse(user);
    }





}
