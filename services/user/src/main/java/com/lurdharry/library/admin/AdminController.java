package com.lurdharry.library.admin;


import com.lurdharry.library.dto.UserResponse;
import com.lurdharry.library.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.lurdharry.library.dto.ResponseDTO;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@EnableMethodSecurity
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    // Admin-only endpoint to update a user's role
    @PutMapping("/update-role")
    @PreAuthorize("hasAuthority('ADMIN_MANAGE_USERS')")
    public ResponseEntity<ResponseDTO> updateRole (@RequestBody @Valid UpdateRoleReq request){

        UserResponse user = adminService.updateUserRole(request);

        ResponseDTO response = ResponseDTO.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Role updated successfully.")
                .data(user)
                .build();

        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN_MANAGE_USERS')")
    public ResponseEntity<ResponseDTO> getAllUsers(Authentication authentication){
        // Log the current authentication and its authorities
        System.out.println("Current Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());

        var users = userService.getAllUsers();
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "users fetched successfully",users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
