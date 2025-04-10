package com.lurdharry.library.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<EmailVerification,String> {

    Optional<EmailVerification> findByEmailAndToken(String email,String token);
    Optional<EmailVerification> findByEmail(String email);
}
