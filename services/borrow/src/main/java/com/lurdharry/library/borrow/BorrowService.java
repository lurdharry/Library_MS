package com.lurdharry.library.borrow;

import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.user.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final UserClient userClient;

    public ResponseDTO fetchUser(UUID userId) {
        // Call the user service through the Feign client
        return userClient.getUserById(userId);
    }

}
