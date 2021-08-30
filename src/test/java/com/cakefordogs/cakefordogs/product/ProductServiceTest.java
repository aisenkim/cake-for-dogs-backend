package com.cakefordogs.cakefordogs.product;

import com.cakefordogs.cakefordogs.product.dto.ProductSaveRequestDto;
import com.cakefordogs.cakefordogs.product.exception.BadRequestException;
import com.cakefordogs.cakefordogs.product.exception.ProductNotFoundException;
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
import static org.mockito.Mockito.*;

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
        ProductSaveRequestDto saveRequestDto = ProductSaveRequestDto.builder()
                .name("Broccoli Cake")
                .description("New release of vegan cake")
                .price(new BigDecimal("35.67"))
                .build();

        // when
        productService.addNewProduct(saveRequestDto);

        // then
        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();

        assertThat(capturedProduct.getName()).isEqualTo(saveRequestDto.toEntity().getName());
    }

    @Test
    void willThrowErrorWhenProductNameExists() {
        // given
        ProductSaveRequestDto saveRequestDto = ProductSaveRequestDto.builder()
                .name("Broccoli Cake")
                .description("New release of vegan cake")
                .price(new BigDecimal("35.67"))
                .build();

        given(productRepository.findProductByName(saveRequestDto.getName()))
                .willReturn(Optional.of(saveRequestDto.toEntity()));

        // when
        // then
        assertThatThrownBy(() -> productService.addNewProduct(saveRequestDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Product Already Exists");

        verify(productRepository, never()).save(any());

    }

    @Test
    void itWillDeleteProductById() {
        // given
        Product newProduct = new Product(1L , "Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));
        when(productRepository.findById(1L)).thenReturn(Optional.of(newProduct));

        // when
        productService.deleteProduct(newProduct.getId());

        // then
        verify(productRepository).deleteById(newProduct.getId());

    }

    @Test
    void shouldThrowErrorWhenProductDoesNotExist() {
        // given
        Product newProduct = new Product(1L , "Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));
        given(productRepository.findById(newProduct.getId())).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> productService.deleteProduct(newProduct.getId()))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Product with id: " + newProduct.getId() + " does not exist.");


    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product newProduct = new Product(1L , "Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));
        given(productRepository.findById(newProduct.getId())).willReturn(Optional.of(newProduct));

        // when
        productService.updateProduct(1L, "New Name", "New Description", new BigDecimal("11.11"));

        // then
        assertThat(newProduct.getName()).isNotEqualTo("Broccoli Cake");
        assertThat(newProduct.getName()).isEqualTo("New Name");
    }

    @Test
    void shouldNotUpdateProductIfLengthOfUpdateParametersAreZero() {
        // given
        Product newProduct = new Product(1L , "Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"));
        given(productRepository.findById(newProduct.getId())).willReturn(Optional.of(newProduct));

        // when
        productService.updateProduct(1L, "", "", new BigDecimal("-1.00"));

        // then
        assertThat(newProduct.getName()).isEqualTo("Broccoli Cake");
        assertThat(newProduct.getDescription()).isEqualTo("New release of vegan cake");
        assertThat(newProduct.getPrice()).isEqualTo(new BigDecimal("35.67"));

    }


}