package com.ecommerce.product_service.infrastructure.security;

import com.ecommerce.product_service.application.service.JwtService;
import com.ecommerce.product_service.domain.port.in.AuthUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
    private final JwtService jwtService;  // Cambiamos AuthUseCase por JwtService
    private static final String BEARER_PREFIX = "Bearer ";
    private static final List<String> PUBLIC_PATHS = Arrays.asList("/api/auth/login", "/api/auth/register");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        return extractAndValidateToken(exchange)
                .flatMap(token -> jwtService.validateToken(token)
                        .flatMap(valid -> {
                            if (valid) {
                                return authenticateAndContinue(exchange, chain, token);
                            }
                            log.error("Invalid token received");
                            return onError(exchange, HttpStatus.UNAUTHORIZED);
                        })
                        .onErrorResume(e -> {
                            log.error("Error validating token: {}", e.getMessage());
                            return onError(exchange, HttpStatus.UNAUTHORIZED);
                        }))
                .switchIfEmpty(onError(exchange, HttpStatus.UNAUTHORIZED));
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    private Mono<String> extractAndValidateToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return Mono.just(authHeader.substring(BEARER_PREFIX.length()));
        }
        return Mono.empty();
    }

    private Mono<Void> authenticateAndContinue(ServerWebExchange exchange, WebFilterChain chain, String token) {
        return jwtService.getUsernameFromToken(token)
                .map(username -> {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );
                    return auth;
                })
                .flatMap(auth -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        return exchange.getResponse().setComplete();
    }
}