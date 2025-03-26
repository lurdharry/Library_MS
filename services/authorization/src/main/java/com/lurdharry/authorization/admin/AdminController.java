package com.lurdharry.authorization.admin;

import com.lurdharry.authorization.dto.ResponseDTO;
import com.lurdharry.authorization.user.UserRequest;
import com.lurdharry.authorization.user.UserResponse;
import com.lurdharry.authorization.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/create-attendant")
    @PreAuthorize("hasAuthority('ADMIN_MANAGE_USERS')")
    public ResponseEntity<?> createAttendant( @RequestBody @Valid UserRequest request){
        UserResponse res = userService.createAttendant(request);

        ResponseDTO response = ResponseDTO.builder()
                .status(HttpStatus.OK.value())
                .message("Attendant registered successfully.")
                .data(res)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // Admin-only endpoint to update a user's role
    @PutMapping("/update-role/{user-id}")
    @PreAuthorize("hasAuthority('ADMIN_MANAGE_USERS')")
    public ResponseEntity<ResponseDTO> updateRole (@RequestBody @Valid UpdateRoleReq request){

        UserResponse user = userService.updateUserRole(request);

        ResponseDTO response = ResponseDTO.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Role updated successfully.")
                .data(user)
                .build();

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }
}
