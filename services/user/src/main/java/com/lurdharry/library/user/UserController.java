package com.lurdharry.library.user;

import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/{user-id}")
    @PreAuthorize("hasAuthority('VIEW_PROFILE')")
    public ResponseEntity<ResponseDTO>findById(
            @PathVariable("user-id") UUID userId
    ){
        var userResponse = userService.findById(userId);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User fetched successfully",userResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('VIEW_PROFILE')")
    public ResponseEntity<ResponseDTO> updateUser(
           @RequestBody @Valid UserRequest request
    ){

        var updatedUser = userService.updateUser(request);

        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "User details updated successfully.",updatedUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
