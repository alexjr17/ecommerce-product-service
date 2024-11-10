//package com.ecommerce.product_service.application.service;
//
//
//import com.ecommerce.product_service.domain.DTO.ProductDTO;
//import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
//import com.ecommerce.product_service.domain.model.Product;
//import com.ecommerce.product_service.domain.port.out.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import java.math.BigDecimal;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    private Product testProduct;
//    private ProductDTO testProductDto;
//
//    @BeforeEach
//    void setUp() {
//        testProduct = Product.builder()
//                .id(1L)
//                .name("Test Product")
//                .price(new BigDecimal("990.99"))
//                .description("Test Description")
//                .stock(10)
//                .build();
//        testProductDto = ProductDTO.builder()
//                .name(testProduct.getName())
//                .description(testProduct.getDescription())
//                .price(testProduct.getPrice())
//                .stock(testProduct.getStock())
//                .build();
//    }
//
//    @Test
//    void createProduct_Success() { // Crear producto
//
//        when(productRepository.create(any(Product.class)))
//                .thenReturn(Mono.just(testProduct));
//
//        StepVerifier.create(productService.createProduct(testProductDto))
//                .expectNext(testProduct)
//                .verifyComplete();
//
//        verify(productRepository, times(1)).create(any(Product.class));
//    }
//
//    @Test
//    void getProductById_Success() { // Consultar producto por id
//        when(productRepository.findById(1L))
//                .thenReturn(Mono.just(testProduct));
//
//        StepVerifier.create(productService.getProductById(1L))
//                .expectNext(testProduct)
//                .verifyComplete();
//
//        verify(productRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void getProductById_NotFound() { // Fallo producto no encontrado
//        when(productRepository.findById(999L))
//                .thenReturn(Mono.empty());
//
//        StepVerifier.create(productService.getProductById(999L))
//                .expectError(ProductNotFoundException.class)
//                .verify();
//
//        verify(productRepository, times(1)).findById(999L);
//    }
//
//    @Test
//    void getAllProducts_Success() { //Listar total productos
//        when(productRepository.findAll())
//                .thenReturn(Flux.just(testProduct));
//
//        StepVerifier.create(productService.getAllProducts())
//                .expectNext(testProduct)
//                .verifyComplete();
//
//        verify(productRepository, times(1)).findAll();
//    }
//
//    @Test
//    void updateProduct_Success() { //Actualizar producto
//        Product updatedProduct = Product.builder()
//                .id(1L)
//                .name("Updated Product")
//                .price(new BigDecimal("1990.99"))
//                .description("Updated Description")
//                .stock(20)
//                .build();
//
//        when(productRepository.update(any(Long.class), any(Product.class)))
//                .thenReturn(Mono.just(updatedProduct));
//
//        StepVerifier.create(productService.updateProduct(1L, testProductDto))
//                .expectNext(updatedProduct)
//                .verifyComplete();
//
//        verify(productRepository, times(1)).update(any(Long.class), any(Product.class));
//    }
//
//    @Test
//    void updateProduct_NotFound() { //Fallo producto no encontrado
//        Product updateProduct = Product.builder()
//                .id(999L)
//                .name("Non Existent Product")
//                .price(new BigDecimal("100.00"))
//                .description("Test")
//                .stock(1)
//                .build();
//
//        when(productRepository.update(any(Long.class), any(Product.class)))
//                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));
//
//        StepVerifier.create(productService.updateProduct(999L, testProductDto))
//                .expectError(ProductNotFoundException.class)
//                .verify();
//
//        verify(productRepository, times(1)).update(any(Long.class), any(Product.class));
//    }
//
//    @Test
//    void deleteProduct_Success() { // Elimnar producto
//        when(productRepository.deleteById(1L))
//                .thenReturn(Mono.empty());
//
//        StepVerifier.create(productService.deleteProduct(1L))
//                .verifyComplete();
//
//        verify(productRepository, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void deleteProduct_NotFound() { // Fallo al elimianar producto
//        when(productRepository.deleteById(999L))
//                .thenReturn(Mono.error(new ProductNotFoundException("Product not found with id: 999")));
//
//        StepVerifier.create(productService.deleteProduct(999L))
//                .expectError(ProductNotFoundException.class)
//                .verify();
//
//        verify(productRepository, times(1)).deleteById(999L);
//    }
//
//    @Test
//    void createProduct_WithNegativePrice() { //Fallo precio negativo
//        ProductDTO invalidProduct = ProductDTO.builder()
//                .name("Invalid Product")
//                .price(new BigDecimal("-100.00"))
//                .description("Test Description")
//                .stock(10)
//                .build();
//
//        when(productRepository.create(any(Product.class)))
//                .thenReturn(Mono.error(new IllegalArgumentException("Price cannot be negative")));
//
//        StepVerifier.create(productService.createProduct(invalidProduct))
//                .expectError(IllegalArgumentException.class)
//                .verify();
//    }
//}
