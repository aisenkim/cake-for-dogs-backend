package com.cakefordogs.cakefordogs.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void itShouldGetAllProducts() {
        // when
        productService.getProducts();
        // then
        verify(productRepository).findAll();
    }

    @Test
    void canAddNewProduct() {
        // given
        Product newProduct = new Product("Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));

        // when
        productService.addNewProduct(newProduct);

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(capturedProduct).isEqualTo(newProduct);
    }

    @Test
    void willThrowErrorWhenProductNameExists() {
        // given
        Product newProduct = new Product("Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));

        given(productRepository.findProductByName(newProduct.getName()))
                .willReturn(Optional.of(newProduct));

        // when
        // then
        assertThatThrownBy(() -> productService.addNewProduct(newProduct))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Product Already Exists");

        verify(productRepository, never()).save(any());

    }
}