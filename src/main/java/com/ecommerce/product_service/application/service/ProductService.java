package com.ecommerce.product_service.application.service;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.in.ProductUseCase;
import com.ecommerce.product_service.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.create(product);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> updateProduct(String id, Product product) {
        return productRepository.update(id, product);
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}