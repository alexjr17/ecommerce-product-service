package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.DTO.ProductDTO;
import com.ecommerce.product_service.domain.exception.NotFoundException;
import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.in.ProductUseCase;
import com.ecommerce.product_service.domain.port.out.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
@Validated
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> createProduct(ProductDTO dto) {

        return productRepository.create(Product.builder().
                name(dto.getName()).
                description(dto.getDescription()).
                price(dto.getPrice()).
                stock(dto.getStock()).
                build());
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> updateProduct(Long id, ProductDTO dto) {
        return productRepository.update(id, Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build());
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }
}