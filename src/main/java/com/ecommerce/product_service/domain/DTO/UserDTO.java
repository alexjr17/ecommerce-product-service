package com.ecommerce.product_service.domain.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String email;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

}
