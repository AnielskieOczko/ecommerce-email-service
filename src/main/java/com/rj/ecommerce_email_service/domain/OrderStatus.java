package com.rj.ecommerce_email_service.domain;

public enum OrderStatus {
    PENDING,        // Order created but not confirmed
    CONFIRMED,      // Order confirmed (payment successful)
    PROCESSING,     // Order is being prepared for shipment
    SHIPPED,        // Order has been shipped
    DELIVERED,      // Order has been delivered
    CANCELLED,      // Order has been cancelled
    REFUNDED,       // Order has been refunded
    FAILED          // Order failed (e.g., payment failed)
}
