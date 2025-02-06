package com.rj.ecommerce_email_service.domain.product;


import com.rj.ecommerce_email_service.domain.product.valueobject.ProductDescription;
import com.rj.ecommerce_email_service.domain.product.valueobject.ProductName;
import com.rj.ecommerce_email_service.domain.product.valueobject.ProductPrice;
import com.rj.ecommerce_email_service.domain.product.valueobject.StockQuantity;
import lombok.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Product {

    Long id;

    ProductName productName;

    ProductDescription productDescription;

    ProductPrice productPrice;

    StockQuantity stockQuantity;

    List<Category> categories = new ArrayList<>();

    List<Image> imageList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String lastModifiedBy;
}