package com.rj.ecommerce_email_service.domain.order;

import com.rj.ecommerce_email_service.domain.OrderStatus;
import com.rj.ecommerce_email_service.domain.PaymentMethod;
import com.rj.ecommerce_email_service.domain.ShippingMethod;
import com.rj.ecommerce_email_service.domain.user.User;
import com.rj.ecommerce_email_service.domain.user.vo.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;

    private User user;

    private List<OrderItem> orderItems = new ArrayList<>();

    private BigDecimal totalPrice;

    private Address shippingAddress;

    private ShippingMethod shippingMethod;

    private PaymentMethod paymentMethod;

    private String paymentTransactionId;

    private LocalDateTime orderDate;

    private OrderStatus orderStatus;

}
