package com.rj.ecommerce_email_service.domain.product.valueobject;

import lombok.Builder;

import java.util.Currency;
import java.util.Objects;

@Builder
public record CurrencyCode(String code) {

    public CurrencyCode {
        Objects.requireNonNull(code, "Currency code cannot be null");
        try {
            Currency.getInstance(code);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + code, e);
        }
    }
}
