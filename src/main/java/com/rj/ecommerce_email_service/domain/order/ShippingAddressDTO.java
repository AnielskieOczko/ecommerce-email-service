package com.rj.ecommerce_email_service.domain.order;

public record ShippingAddressDTO(
        String street,
        String city,
        String zipCode,
        String country) {
}
