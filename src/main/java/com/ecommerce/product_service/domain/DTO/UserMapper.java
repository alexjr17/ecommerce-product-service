package com.ecommerce.product_service.domain.DTO;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.UserEntity;

public class UserMapper {

    // Método para mapear UserEntity a UserDTO
    public static UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
//                .createdAt(userEntity.getCreatedAt())
//                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    // Método para mapear UserDTO a UserEntity
    public static UserEntity toEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .build(); // No se incluye el campo password aquí
    }
}