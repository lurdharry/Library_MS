package com.lurdharry.user.user;

import com.lurdharry.user.dto.UserRequest;
import com.lurdharry.user.dto.UserResponse;
import com.lurdharry.user.exception.ResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse findById(String userId) {

        return repository.findById(userId).map(mapper::toUserResponse)
                .orElseThrow(
                        () -> new ResponseException("User not found", HttpStatus.BAD_REQUEST)
                );
    }

    public UserResponse  updateUser(@Valid UserRequest request) {

        var user = repository.findById(request.id())
                .orElseThrow( () -> new ResponseException("User not found", HttpStatus.BAD_REQUEST));

        mapper.mergeUser(user, request);
        var savedUser = repository.save(user);
        return mapper.toUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        return repository.findAll().stream().map(mapper::toUserResponse).collect(Collectors.toList());
    }
}
