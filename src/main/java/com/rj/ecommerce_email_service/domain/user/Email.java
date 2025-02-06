package com.rj.ecommerce_email_service.domain.user;

import lombok.Builder;

@Builder
public record Email(String value) {
    public static Email of(String email) {
        return new Email(email);
    }

    private static boolean isValidEmail(String email) {
        // Add your email validation implementation here
        return true;
    }
}
