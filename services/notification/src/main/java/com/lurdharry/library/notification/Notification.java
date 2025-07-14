package com.lurdharry.library.notification;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "notifications")
@SuperBuilder
@Getter
@Setter
public class Notification {
    @Id
    private String id;

    private NotificationType type;

    private LocalDateTime notificationDate;
}
