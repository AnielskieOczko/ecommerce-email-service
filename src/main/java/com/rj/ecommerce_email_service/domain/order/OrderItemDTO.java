package com.rj.ecommerce_email_service.domain.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDTO(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        int quantity,
        BigDecimal price) {
}
