package com.rj.ecommerce_email_service.email.listener;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void processEmail(EmailMessageDTO emailMessage) {

        validateEmailMessage(emailMessage);

        try {
            log.info("Started processing email: {}", emailMessage);
            Context context = new Context();

            context.setVariables(emailMessage.data());

            String htmlBody = templateEngine.process(
                    emailMessage.template(),
                    context
            );

            sendEmail(emailMessage, htmlBody);

            log.info("Email sent");
        } catch (Exception e) {
            // Add error handling/logging
            log.error("Error processing email: {}", e.getMessage(), e);  // Log full stack trace
        }
    }

    private void validateEmailMessage(EmailMessageDTO emailMessage) {
        // Perform basic validation
        if (Objects.isNull(emailMessage)) {
            throw new IllegalArgumentException("Email message cannot be null");
        }
        if (emailMessage.to() == null || emailMessage.to().isBlank()) {
            throw new IllegalArgumentException("Recipient email address is required");
        }
        if (emailMessage.template() == null || emailMessage.template().isBlank()) {
            throw new IllegalArgumentException("Email template is required");
        }
    }

    private void sendEmail(EmailMessageDTO emailMessageDTO, String htmlBody) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);

        helper.setTo(emailMessageDTO.to());
        helper.setSubject(emailMessageDTO.subject());
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
}
