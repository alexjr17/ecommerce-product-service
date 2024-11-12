package com.ecommerce.product_service.domain.port.in;

import com.ecommerce.product_service.domain.DTO.AuthDTO;
import com.ecommerce.product_service.domain.DTO.AuthResponseDTO;
import com.ecommerce.product_service.domain.DTO.RegisterDTO;
import reactor.core.publisher.Mono;

public interface AuthUseCase {
    Mono<AuthResponseDTO> authenticate(AuthDTO authDTO);
    Mono<AuthResponseDTO> register(RegisterDTO registerDTO);
}
