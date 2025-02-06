package com.rj.ecommerce_email_service.domain.product.valueobject;

import lombok.Builder;

@Builder
public record ProductPrice(CurrencyCode currency) {
}
