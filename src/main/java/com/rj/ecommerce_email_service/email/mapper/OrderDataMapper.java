package com.rj.ecommerce_email_service.email.mapper;

import com.rj.ecommerce_email_service.domain.OrderStatus;
import com.rj.ecommerce_email_service.domain.PaymentMethod;
import com.rj.ecommerce_email_service.domain.ShippingMethod;
import com.rj.ecommerce_email_service.domain.order.*;
import com.rj.ecommerce_email_service.domain.product.Product;
import com.rj.ecommerce_email_service.domain.product.valueobject.ProductName;
import com.rj.ecommerce_email_service.domain.user.Email;
import com.rj.ecommerce_email_service.domain.user.User;
import com.rj.ecommerce_email_service.domain.user.vo.Address;
import com.rj.ecommerce_email_service.domain.user.vo.ZipCode;
import com.rj.ecommerce_email_service.email.listener.EmailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class OrderDataMapper {

    public OrderDTO mapOrderData(EmailRequest emailMessage) {
        Map<String, Object> data = emailMessage.data();
        if (!(data.get("order") instanceof Map)) {
            log.error("Invalid order data: Expected a Map for 'order' but received: {}", data.get("order").getClass().getName());
            return null;
        }

        Map<String, Object> orderData = (Map<String, Object>) data.get("order");

        ShippingAddressDTO shippingAddressDTO = mapShippingAddressDTO(orderData.get("shippingAddress"));
        List<OrderItemDTO> orderItemDTOs = mapOrderItemDTOs(orderData.get("orderItems"));

        return new OrderDTO(
                convertToLong(orderData.get("id")),
                convertToLong(orderData.get("userId")),
                emailMessage.to(),
                orderItemDTOs,
                convertToBigDecimal(orderData.get("totalPrice")),
                shippingAddressDTO,
                convertToShippingMethod(orderData.get("shippingMethod")),
                convertToPaymentMethod(orderData.get("paymentMethod")),
                (String) orderData.get("paymentTransactionId"),
                convertToLocalDateTime(orderData.get("orderDate")),
                convertToOrderStatus(orderData.get("orderStatus")),
                convertToLocalDateTime(orderData.get("createdAt")),
                convertToLocalDateTime(orderData.get("updatedAt"))
        );
    }

    private ShippingAddressDTO mapShippingAddressDTO(Object addressData) {
        if (!(addressData instanceof Map)) {
            log.error("Invalid address data: Expected a Map but received: {}", addressData.getClass().getName());
            return null;
        }

        Map<String, Object> addressMap = (Map<String, Object>) addressData;
        return new ShippingAddressDTO(
                (String) addressMap.get("street"),
                (String) addressMap.get("city"),
                (String) addressMap.get("zipCode"),
                (String) addressMap.get("country")
        );
    }

    private List<OrderItemDTO> mapOrderItemDTOs(Object orderItemsData) {
        if (!(orderItemsData instanceof List)) {
            log.error("Invalid order items data: Expected a List but received: {}", orderItemsData.getClass().getName());
            return null;
        }

        List<Map<String, Object>> orderItemsList = (List<Map<String, Object>>) orderItemsData;
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (Object itemData : orderItemsList) {
            if (!(itemData instanceof Map)) {
                log.error("Invalid order item data: Expected a Map but received: {}", itemData.getClass().getName());
                return null;
            }

            Map<String, Object> itemMap = (Map<String, Object>) itemData;
            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    convertToLong(itemMap.get("id")),
                    convertToLong(itemMap.get("orderId")),
                    convertToLong(itemMap.get("productId")),
                    (String) itemMap.get("productName"),
                    (Integer) itemMap.get("quantity"),
                    convertToBigDecimal(itemMap.get("price"))
            );
            orderItemDTOs.add(orderItemDTO);
        }
        return orderItemDTOs;
    }

    // ... (Other helper methods: convertToLong, convertToBigDecimal, convertToLocalDateTime, etc.)
    public Long convertToLong(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }

    public BigDecimal convertToBigDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof String) {
            return new BigDecimal((String) value);
        } else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        }
        return null;
    }

    public LocalDateTime convertToLocalDateTime(Object obj) {
        if (obj instanceof List) {
            List<Integer> dateParts = (List<Integer>) obj;
            return LocalDateTime.of(dateParts.get(0), dateParts.get(1), dateParts.get(2), dateParts.get(3), dateParts.get(4), dateParts.get(5));
        } else if (obj instanceof Long) {
            // Assuming timestamp in milliseconds
            return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli((Long) obj), ZoneId.systemDefault());
        }
        return null;
    }

    private ShippingMethod convertToShippingMethod(Object value) {
        if (value instanceof String) {
            try {
                return ShippingMethod.valueOf((String) value);
            } catch (IllegalArgumentException e) {
                log.error("Invalid shipping method value: {}", value, e);
                return null; // Or throw an exception
            }
        }
        log.error("Invalid shipping method data type: Expected String but received: {}", value.getClass().getName());
        return null; // Or throw an exception
    }

    private PaymentMethod convertToPaymentMethod(Object value) {
        if (value instanceof String) {
            try {
                return PaymentMethod.valueOf((String) value);
            } catch (IllegalArgumentException e) {
                log.error("Invalid payment method value: {}", value, e);
                return null; // Or throw an exception
            }
        }
        log.error("Invalid payment method data type: Expected String but received: {}", value.getClass().getName());
        return null; // Or throw an exception
    }

    private OrderStatus convertToOrderStatus(Object value) {
        if (value instanceof String) {
            try {
                return OrderStatus.valueOf((String) value);
            } catch (IllegalArgumentException e) {
                log.error("Invalid order status value: {}", value, e);
                return null; // Or throw an exception
            }
        }
        log.error("Invalid order status data type: Expected String but received: {}", value.getClass().getName());
        return null; // Or throw an exception
    }
}
