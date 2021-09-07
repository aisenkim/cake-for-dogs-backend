package com.cakefordogs.cakefordogs.discount;

import com.cakefordogs.cakefordogs.discount.dto.SaveDiscountDto;
import com.cakefordogs.cakefordogs.discount.exception.DiscountNotFoundException;
import com.cakefordogs.cakefordogs.product.exception.BadRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private DiscountRepository discountRepository;
    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService(discountRepository);
    }

    @Test
    void itShouldGetAllDiscounts() {
        // when
        discountService.getDiscounts();

        // then
        verify(discountRepository).findAll();
    }

    @Test
    void itShouldAddDiscount() {
        // given
        SaveDiscountDto discountDto = SaveDiscountDto.builder()
                .name("new discount")
                .description("New cake release")
                .discountPercent(new BigDecimal("0.05"))
                .build();

        // when
        discountService.addDiscount(discountDto);

        // then
        ArgumentCaptor<Discount> discountArgumentCaptor = ArgumentCaptor.forClass(Discount.class);

        verify(discountRepository).save(discountArgumentCaptor.capture());
        Discount capturedDiscount = discountArgumentCaptor.getValue();

        assertThat(capturedDiscount.getName()).isEqualTo(discountDto.getName());
    }

    @Test
    void itShouldThrowErrorIfDiscountExists() {
        // given
        SaveDiscountDto saveDiscountDto = SaveDiscountDto.builder()
                .name("new discount")
                .description("new customer")
                .discountPercent(new BigDecimal("0.3"))
                .build();

        given(discountRepository.findDiscountByName(saveDiscountDto.getName()))
                .willReturn(Optional.of(saveDiscountDto.toEntity()));

        // when
        // then
        assertThatThrownBy(() -> discountService.addDiscount(saveDiscountDto))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Discount already exists");

        verify(discountRepository, never()).save(any());
    }

    @Test
    void itShouldDeleteDiscountById() {
        // given
        Discount discount = new Discount("new discount", "new customer", new BigDecimal("0.3"));
        when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));

        // when
        discountService.deleteDiscount(discount.getId());

        // then
        verify(discountRepository).deleteById(discount.getId());
    }

    @Test
    void itShouldThrowErrorWhenDiscountDoesNotExist() {
        // given
        Discount discount = Discount.builder()
                .name("new discount")
                .description("new customer")
                .discountPercent(new BigDecimal("0.3"))
                .build();
        given(discountRepository.findById(discount.getId())).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> discountService.deleteDiscount(discount.getId()))
                .isInstanceOf(DiscountNotFoundException.class)
                .hasMessageContaining("Discount does not exist");
    }

    @Test
    void itShouldUpdateDiscount() {
       // given
        Discount discount = Discount.builder()
                .name("new discount")
                .description("new customer")
                .discountPercent(new BigDecimal("0.3"))
                .build();
        given(discountRepository.findById(discount.getId())).willReturn(Optional.of(discount));

        // when
        discountService.updateDiscount(discount.getId(), "updated discount", "updated customer", new BigDecimal("0.00001"));

        // then
        assertThat(discount.getName()).isEqualTo("updated discount");
        assertThat(discount.getDescription()).isEqualTo("updated customer");
        assertThat(discount.getDiscountPercent()).isEqualTo(new BigDecimal("0.00001"));
    }

    @Test
    void itShouldNotUpdateDiscountIfDataIsWrong() {
       // given
        Discount discount = Discount.builder()
                .name("new discount")
                .description("new customer")
                .discountPercent(new BigDecimal("0.3"))
                .build();
        given(discountRepository.findById(discount.getId())).willReturn(Optional.of(discount));

        // when
        discountService.updateDiscount(discount.getId(), "", "", new BigDecimal("0.00"));

        // then
        assertThat(discount.getName()).isEqualTo("new discount");
        assertThat(discount.getDescription()).isEqualTo("new customer");
        assertThat(discount.getDiscountPercent()).isEqualTo(new BigDecimal("0.3"));
    }

    @Test
    void itShouldUpdateDiscountAndUpdateModifedTime() {
        // given
        Discount discount = Discount.builder()
                .name("new discount")
                .description("new customer")
                .discountPercent(new BigDecimal("0.3"))
                .build();

        Timestamp beforeUpdateModifiedTime = discount.getModified_at();

        given(discountRepository.findById(discount.getId())).willReturn(Optional.of(discount));

        // when
        discountService.updateDiscount(discount.getId(), "", "", new BigDecimal("0.00"));

        // then
        assertThat(discount.getModified_at()).isNotEqualTo(beforeUpdateModifiedTime);

    }


}