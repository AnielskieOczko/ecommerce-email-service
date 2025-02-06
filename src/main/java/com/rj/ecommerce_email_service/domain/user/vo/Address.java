package com.rj.ecommerce_email_service.domain.user.vo;

import lombok.Builder;

@Builder
public record Address(
        String street,
        String city,
        ZipCode zipCode,
        String country
) { }
