package com.rj.ecommerce_email_service.email.listener;

import com.rj.ecommerce_email_service.domain.order.OrderDTO;
import com.rj.ecommerce_email_service.email.mapper.OrderDataMapper;
import com.rj.ecommerce_email_service.email.sender.EmailNotificationService;
import com.rj.ecommerce_email_service.email.sender.EmailStatus;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailNotificationService emailNotificationService;
    private final OrderDataMapper orderDataMapper;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void processEmail(EmailRequest emailMessage) {

        validateEmailMessage(emailMessage);
        Long orderId = null;

        try {
            log.info("Started processing email: {}", emailMessage);

            // Attempt to get the order ID early (later used to send email send failure notification
            Object orderObj = emailMessage.data().get("order");
            if (orderObj instanceof Map) {
                orderId = orderDataMapper.convertToLong(((Map<?, ?>) orderObj).get("id"));
            }

            if (orderId == null) {
                log.error("Could not extract order ID from email message: {}", emailMessage);
                // Consider throwing an exception or handling it differently
                throw new RuntimeException("Could not extract order ID");
            }

            Context context = new Context();

            // Use the mapper to map the data
            OrderDTO order = orderDataMapper.mapOrderData(emailMessage);

            context.setVariable("order", order);

            String htmlBody = templateEngine.process(
                    emailMessage.template(),
                    context
            );

            sendEmail(emailMessage, htmlBody);

            emailNotificationService.sendEmailConfirmation(
                    order.id(),
                    EmailStatus.SENT
            );

            log.info("Email sent");
        } catch (Exception e) {
            // Add error handling/logging
            log.error("Error processing email: {}", e.getMessage(), e);  // Log full stack trace
            emailNotificationService.sendEmailConfirmation(
                    orderId,
                    EmailStatus.FAILED
            );
        }
    }

    private void validateEmailMessage(EmailRequest emailMessage) {
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

    private void sendEmail(EmailRequest emailMessageDTO, String htmlBody) throws MessagingException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true);

        helper.setTo(emailMessageDTO.to());
        helper.setSubject(emailMessageDTO.subject());
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
}
