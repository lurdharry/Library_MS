package com.lurdharry.library.auth;


import com.lurdharry.library.kafka.PasswordConfirmation;
import com.lurdharry.library.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthMapper {

    public PasswordConfirmation toKafkaUser(User user) {
        return new PasswordConfirmation(
             new com.lurdharry.library.kafka.User(
                     user.getId().toString(),
                     user.getFirstname(),
                     user.getLastname(),
                     user.getEmail()
             )
        );
    }
}
