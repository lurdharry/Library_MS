package com.lurdharry.library.notification;


import com.lurdharry.library.kafka.user.PasswordConfirmation;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Document(collection = "notifications")
@TypeAlias("passwordNotification")
public class PasswordNotification extends Notification {
    private PasswordConfirmation passwordConfirmation;
}
