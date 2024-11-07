package com.ecommerce.product_service.domain.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class Order {
    String id;
    String userId;
    String productId;
    int quantity;
    BigDecimal total;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
