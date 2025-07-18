package com.lurdharry.library.user;

import com.lurdharry.library.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(
        name = "user-service",
        url = "${application.config.user-url}",
        configuration = FeignClientConfig.class,
        contextId = "userServiceClient"
)
public interface UserClient {

    /**
     * Calls the user service to retrieve user information by user ID.
     *
     * @param userId The ID of the user.
     * @return A ResponseDTO containing the user details.
     */
    @GetMapping("/{user-id}")
    ResponseDTO<UserResponse> getUserById(
            @PathVariable("user-id") String userId
    );
}
