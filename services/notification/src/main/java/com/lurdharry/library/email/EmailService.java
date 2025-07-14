package com.lurdharry.library.email;

import com.lurdharry.library.kafka.borrow.ApprovalStatus;
import com.lurdharry.library.kafka.borrow.Book;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lurdharry.library.email.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;


    @Async
    public void sendOrderSuccessEmail(
            String destinationEmail,
            String username,
            String bookOrderRef,
            List<Book> books
    ) {

        final String templateName = BORROW_CONFIRMATION.getTemplate();
        final String subject = BORROW_CONFIRMATION.getSubject();

        Map<String,Object> variables = new HashMap<>();
        variables.put("username",username);
        variables.put("bookOrderRef",bookOrderRef);
        variables.put("books",books);

        sendEmailTemplate(destinationEmail,templateName,subject,variables);
    }

    @Async
    public void sendStatusConfirmation(
            String destinationEmail,
            String username,
            String bookOrderRef,
            ApprovalStatus status,
            List<Book> books
    )  {

        final String templateName = STATUS_UPDATE.getTemplate();
        final String subject = STATUS_UPDATE.getSubject();

        Map<String, Object> variables = new HashMap<>();
        variables.put("username",username);
        variables.put("bookOrderRef",bookOrderRef);
        variables.put("books",books);
        variables.put("status",status);

        sendEmailTemplate(destinationEmail,templateName,subject,variables);
    }

    public void sendChangePasswordConfirmation(
            String destinationEmail,
            String username
    ){
        final String templateName = PASSWORD_UPDATE_CONFIRMATION.getTemplate();
        final String subject = PASSWORD_UPDATE_CONFIRMATION.getSubject();

        Map<String, Object> variables = new HashMap<>();
        variables.put("username",username);

        sendEmailTemplate(destinationEmail,templateName,subject,variables);
    }

    private void sendEmailTemplate(
         String   destinationEmail,
         String templateName,
         String subject,
         Map<String, Object> variables
    )  {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    UTF_8.name()
            );

            messageHelper.setFrom("contact@library.com");
            messageHelper.setSubject(subject);
            messageHelper.setTo(destinationEmail);

            Context context = new Context();
            context.setVariables(variables);
            String htmlTemplate = templateEngine.process(templateName,context);
            messageHelper.setText(htmlTemplate,true);

            mailSender.send(mimeMessage);
            log.info("Email successfully sent to {} with template {}",destinationEmail,templateName);
        } catch (MessagingException e) {
            log.warn("Failed to send email to {}: {}", destinationEmail, e.getMessage(), e);
        }
    }
}
