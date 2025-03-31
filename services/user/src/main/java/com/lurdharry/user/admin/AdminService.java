package com.lurdharry.user.admin;


import com.lurdharry.user.dto.UserResponse;
import com.lurdharry.user.exception.ResponseException;
import com.lurdharry.user.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleEntityRepository entityRepository;
    private final UserMapper mapper;


    //    // admin only
    public UserResponse updateUserRole(@Valid UpdateRoleReq request){
        RoleEntity role = entityRepository.findByName(request.role())
                .orElseThrow(()->new ResponseException("Role not found: " + request.role().name(),HttpStatus.NOT_FOUND));
        User user = userRepository.findById(request.userId()).orElseThrow(
                ()-> new ResponseException("User not found: " + request.userId(),HttpStatus.NOT_FOUND)
        );
        user.setRole(role);

        return mapper.toUserResponse(userRepository.save(user));

    }


}
