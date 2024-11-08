package com.ecommerce.product_service.infrastructure.adapter.in.web;

import com.ecommerce.product_service.domain.DTO.ProductDTO;
import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.port.in.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@WebFluxTest
@ContextConfiguration(classes = {ProductController.class})
@AutoConfigureWebTestClient
class ProductControllerTest {
//Pruebas Http con webTestClient
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductUseCase productUseCase;


    private Product testProduct;
    private ProductDTO testProductDto;

    @BeforeEach
    void setUp() {
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(new BigDecimal("990.99"))
                .description("Test Description")
                .stock(10)
                .build();

        testProductDto = ProductDTO.builder()
                .name(testProduct.getName())
                .description(testProduct.getDescription())
                .price(testProduct.getPrice())
                .stock(testProduct.getStock())
                .build();
    }

    @Test
    void createProduct_Success() { //Test crear producto
        when(productUseCase.createProduct(testProductDto)
                .thenReturn(Mono.just(testProductDto)));

        webTestClient.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testProductDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .isEqualTo(testProduct);

        verify(productUseCase, times(1)).createProduct(testProductDto);
    }

    @Test
    void getProductById_Success() { //Filtrar por id
        when(productUseCase.getProductById(1L))
                .thenReturn(Mono.just(testProduct));

        webTestClient.get()
                .uri("/api/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class)
                .isEqualTo(testProduct);

        verify(productUseCase, times(1)).getProductById(1L);
    }

    @Test
    void getProductById_NotFound() { //Fallo producto no encontrado
        when(productUseCase.getProductById(999L))
                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));

        webTestClient.get()
                .uri("/api/products/999")
                .exchange()
                .expectStatus().isNotFound();

        verify(productUseCase, times(1)).getProductById(999L);
    }

    @Test
    void getAllProducts_Success() {//Listar Total productos
        when(productUseCase.getAllProducts())
                .thenReturn(Flux.just(testProduct));

        webTestClient.get()
                .uri("/api/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .contains(testProduct);

        verify(productUseCase, times(1)).getAllProducts();
    }
}
