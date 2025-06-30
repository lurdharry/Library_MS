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

import static com.lurdharry.library.email.EmailTemplates.BORROW_CONFIRMATION;
import static com.lurdharry.library.email.EmailTemplates.STATUS_UPDATE;
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
            String userName,
            String bookOrderRef,
            List<Book> books
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );
        messageHelper.setFrom("contact@library.com");

        final String templateName = BORROW_CONFIRMATION.getTemplate();

        Map<String,Object> variables = new HashMap<>();
        variables.put("userName",userName);
        variables.put("bookOrderRef",bookOrderRef);
        variables.put("books",books);

        Context context = new Context();
        context.setVariables(variables);

        messageHelper.setSubject(BORROW_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName,context);
            messageHelper.setText(htmlTemplate,true);
            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);

            log.info("INFO - Email successfully sent to {} with template {}", destinationEmail, templateName);
        } catch (MessagingException e){
            log.warn("WARNING - cannot send email to {}",destinationEmail);
        }
    }

    @Async
    public void sendStatusConfirmation(
            String destinationEmail,
            String userName,
            String bookOrderRef,
            ApprovalStatus status,
            List<Book> books
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name()
        );

        messageHelper.setFrom("contact@library.com");

        final String templateName = STATUS_UPDATE.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("userName",userName);
        variables.put("bookOrderRef",bookOrderRef);
        variables.put("books",books);
        variables.put("status",status);

        Context context = new Context();
        context.setVariables(variables);
    }
}
