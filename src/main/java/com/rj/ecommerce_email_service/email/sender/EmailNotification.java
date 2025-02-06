package com.rj.ecommerce_email_service.email.sender;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
public record EmailNotification(
        Long orderId,
        EmailStatus status,
        LocalDateTime timestamp
) {
}
