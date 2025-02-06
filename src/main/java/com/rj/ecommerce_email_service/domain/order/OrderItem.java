package com.rj.ecommerce_email_service.domain.order;

import com.rj.ecommerce_email_service.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;

    private Order order;

    private Product product;

    private int quantity;

    private BigDecimal price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String lastModifiedBy;
}
