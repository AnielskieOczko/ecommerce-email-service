package com.rj.ecommerce_email_service.domain.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    Long id;

    String name;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String lastModifiedBy;
}
