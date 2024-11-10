package com.ecommerce.product_service.domain.port.out;

import com.ecommerce.product_service.domain.DTO.AuthDTO;
import com.ecommerce.product_service.domain.DTO.AuthResponseDTO;
import com.ecommerce.product_service.domain.model.User;
import com.ecommerce.product_service.infrastructure.adapter.out.persistence.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserEntity> userByEmail(String email);
    Mono<User> save(User user);

    Mono<Boolean> existsByEmail(String email);
}
