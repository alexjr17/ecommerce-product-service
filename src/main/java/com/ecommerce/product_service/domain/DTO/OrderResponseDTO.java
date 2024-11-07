package com.ecommerce.product_service.domain.DTO;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long userId; // Mantenemos el ID de usuario
    private String productId; // Mantenemos el ID del producto
    private UserDTO user; // Detalle completo del usuario
    private ProductDTO product; // Detalle completo del producto
    private int quantity;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
