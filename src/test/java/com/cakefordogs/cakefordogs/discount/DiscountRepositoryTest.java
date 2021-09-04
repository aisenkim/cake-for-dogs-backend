package com.cakefordogs.cakefordogs.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DiscountRepositoryTest {

    @Autowired
    private DiscountRepository discountRepository;

    @AfterEach
    void tearDown() {
        discountRepository.deleteAll();
    }

    @Test
    void itShouldFindDiscountByName() {
        // given
        Discount discount = Discount.builder()
                .name("new discount")
                .description("new release")
                .discountPercent(new BigDecimal("0.25"))
                .build();

        discountRepository.save(discount);

        // when
        Optional<Discount> foundDiscount = discountRepository.findDiscountByName("new discount");

        // then
        assertThat(foundDiscount).isPresent();

    }

}