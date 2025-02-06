package com.rj.ecommerce_email_service.domain.order;

import com.rj.ecommerce_email_service.domain.OrderStatus;
import com.rj.ecommerce_email_service.domain.PaymentMethod;
import com.rj.ecommerce_email_service.domain.ShippingMethod;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderDTO(
        Long id,

        Long userId,

        String email,

        List<OrderItemDTO> orderItems,

        BigDecimal totalPrice,

        ShippingAddressDTO shippingAddress,

        ShippingMethod shippingMethod,

        PaymentMethod paymentMethod,

        String paymentTransactionId, // Nullable

        LocalDateTime orderDate,

        OrderStatus orderStatus,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
