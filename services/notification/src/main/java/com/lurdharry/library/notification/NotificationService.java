package com.lurdharry.library.notification;

import com.lurdharry.library.email.EmailService;
import com.lurdharry.library.kafka.borrow.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.borrow.BorrowStatusConfirmation;
import com.lurdharry.library.kafka.user.PasswordConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository repository;
    private final EmailService emailService;
    private final NotificationMapper mapper;


    @Transactional
    public void handleBorrowOrderConfirmation(BorrowOrderConfirmation confirmation){
        log.info("Received borrow-order-topic: {}", confirmation);

        //        save the notification to the storage
        repository.save(mapper.toOrderNotification(confirmation));
        // then send email
        var username = confirmation.user().firstname() + " " + confirmation.user().lastname();
        emailService.sendOrderSuccessEmail(
                confirmation.user().email(),
                username,
                confirmation.bookOrderRef(),
                confirmation.books()
        );
    }

    @Transactional
    public void handleBorrowStatusConfirmation(BorrowStatusConfirmation confirmation){
        log.info("Received order-status-topic: {}", confirmation);

        //  save the notification to the storage
        repository.save(mapper.toStatusNotification(confirmation));

        // then send email
        var username = confirmation.user().firstname() + " " + confirmation.user().lastname();
        emailService.sendStatusConfirmation(
                confirmation.user().email(),
                username,
                confirmation.bookOrderRef(),
                confirmation.status(),
                confirmation.books()
        );

    }

    @Transactional
    public  void  handleChangePassword(PasswordConfirmation confirmation){
        log.info("Received password-update-topic: {}", confirmation);

        repository.save(mapper.toPasswordNotification(confirmation));

        var username = confirmation.user().firstname() + " " + confirmation.user().lastname();

        emailService.sendChangePasswordConfirmation(
                confirmation.user().email(),
                username
        );
    }
}
