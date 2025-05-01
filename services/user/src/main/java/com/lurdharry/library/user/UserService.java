package com.lurdharry.library.user;

import com.lurdharry.library.dto.UserRequest;
import com.lurdharry.library.dto.UserResponse;
import com.lurdharry.library.exception.ResponseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse findById(UUID userId) {

        return repository.findById(userId).map(mapper::toUserResponse)
                .orElseThrow(
                        () -> new ResponseException("User not found", HttpStatus.BAD_REQUEST)
                );
    }

    public UserResponse  updateUser(@Valid UserRequest request) {

        var user = repository.findById(request.userId())
                .orElseThrow( () -> new ResponseException("User not found", HttpStatus.BAD_REQUEST));

        mapper.mergeUser(user, request);
        var savedUser = repository.save(user);
        return mapper.toUserResponse(savedUser);
    }

    public List<UserResponse> getAllUsers() {

        return repository.findAll().stream().map(mapper::toUserResponse).collect(Collectors.toList());
    }
}
