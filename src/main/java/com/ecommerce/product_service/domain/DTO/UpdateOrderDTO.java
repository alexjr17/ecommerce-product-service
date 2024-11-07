package com.ecommerce.product_service.domain.DTO;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UpdateOrderDTO {
    int quantity;
    BigDecimal total;
    String status;
}
