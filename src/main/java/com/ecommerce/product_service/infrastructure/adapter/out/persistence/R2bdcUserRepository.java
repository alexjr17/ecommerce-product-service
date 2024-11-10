package com.ecommerce.product_service.infrastructure.adapter.out.persistence;

import com.ecommerce.product_service.domain.DTO.AuthDTO;
import com.ecommerce.product_service.domain.DTO.AuthResponseDTO;
import com.ecommerce.product_service.domain.model.Order;
import com.ecommerce.product_service.domain.model.User;
import com.ecommerce.product_service.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class R2bdcUserRepository implements UserRepository {
    private final R2dbcEntityTemplate template;

    @Override
    public Mono<UserEntity> userByEmail(String email) {
        return template.selectOne(
                        Query.query(where("email").is(email)),
                        UserEntity.class
                )
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity entity = mapToEntity(user);
        return template.insert(entity).map(this::mapToModel);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return template.exists(
                Query.query(where("email").is(email)),
                UserEntity.class
        );
    }

    private User mapToModel(UserEntity userEntity) {
        return User.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    private UserEntity mapToEntity(User user) {
        return UserEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
