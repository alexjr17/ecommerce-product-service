package com.ecommerce.product_service.infrastructure.adapter.out.persistence;

import com.ecommerce.product_service.domain.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class OrderEntity {
    @Id
    @Column("id")
    @NotBlank(message = "Order ID cannot be blank")
    private String id;

    @Column("user_id")
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @Column("product_id")
    @NotBlank(message = "Product ID cannot be blank")
    private String productId;

    @Column("quantity")
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Column("total")
    @NotNull(message = "Total cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than 0")
    private BigDecimal total;

    @Column("status")
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private Product product;
}
