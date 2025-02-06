package com.rj.ecommerce_email_service.email.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.rj.ecommerce_email_service.email.listener.RabbitMQConfig.NOTIFICATION_EXCHANGE;
import static com.rj.ecommerce_email_service.email.listener.RabbitMQConfig.NOTIFICATION_ROUTING_KEY;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationService {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmailConfirmation(Long orderId, EmailStatus status) {
        EmailNotification notification = EmailNotification.builder()
                .orderId(orderId)
                .status(status)
                .timestamp(LocalDateTime.now())
                .build();

        log.info("Sending email notification for order: {}", orderId);
        rabbitTemplate.convertAndSend(NOTIFICATION_EXCHANGE, NOTIFICATION_ROUTING_KEY, notification);
    }

}
