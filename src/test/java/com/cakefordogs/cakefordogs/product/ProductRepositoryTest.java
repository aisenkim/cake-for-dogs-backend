package com.cakefordogs.cakefordogs.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void itShouldFindProductByName() {
        // given
        Product newProduct = new Product("Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"), null);
        productRepository.save(newProduct);

        // when
        Optional<Product> foundProduct = productRepository.findProductByName(newProduct.getName());

        // then
        assertThat(foundProduct).isPresent();

    }
    @Test
    void itShouldReturnEmptyWhenProductIsNotPresent() {
        // given
        Product newProduct = new Product("Broccoli Cake", "New release of vegan cake", new BigDecimal("35.67"), null);

        // when
        Optional<Product> foundProduct = productRepository.findProductByName(newProduct.getName());

        // then
        assertThat(foundProduct).isNotPresent();

    }
}