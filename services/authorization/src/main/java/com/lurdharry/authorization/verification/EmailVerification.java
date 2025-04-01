package com.lurdharry.authorization.verification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "email_verification",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email","id"})
        }
)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;
    private String token;
    private LocalDateTime expiryDate;
}
