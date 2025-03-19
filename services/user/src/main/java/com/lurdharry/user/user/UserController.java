package com.lurdharry.user.user;

import com.lurdharry.user.dto.ResponseDTO;
import com.lurdharry.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{user-id}")
    public ResponseEntity<ResponseDTO>findById(
            @PathVariable("user-id")String userId
    ){
        var userResponse = userService.findById(userId);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User fetched successfully",userResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(
           @RequestBody @Valid UserRequest request
    ){

        var updatedUser = userService.updateUser(request);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User details updated successfully.",updatedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllUsers(){
        var users = userService.getAllUsers();
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "users fetched successfully",users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
