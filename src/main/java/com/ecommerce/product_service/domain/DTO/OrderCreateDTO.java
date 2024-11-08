package com.ecommerce.product_service.domain.DTO;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class OrderCreateDTO {

    @NotNull(message = "Product ID is mandatory")
    Long productId;

    @Positive(message = "Quantity must be greater than zero")
    int quantity;

    @NotNull(message = "Total is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than zero")
    BigDecimal total;
}
