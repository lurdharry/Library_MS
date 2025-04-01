package com.lurdharry.authorization.verification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
//    private final JavaMailSender mailSender;

    public void sendVerificationCode (String to, String code){
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(to);
//        mailMessage.setSubject("Your Email Verification Code");
//        mailMessage.setText("Your verification code is: " + code + ". It expires in 15 minutes.");
//        mailSender.send(mailMessage);

    }
}
