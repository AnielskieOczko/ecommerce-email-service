package com.rj.ecommerce_email_service.domain.product.valueobject;


import lombok.Builder;

import java.math.BigDecimal;
import java.util.Objects;

@Builder
public record Amount(BigDecimal value) {

    public Amount {
        Objects.requireNonNull(value, "Amount value cannot be null");
        if(value.scale() > 2 ) {
            throw new IllegalArgumentException("incorrect number of decimal places");
        }
    }
}
