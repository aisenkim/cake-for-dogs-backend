package com.cakefordogs.cakefordogs.product.dto;

import com.cakefordogs.cakefordogs.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class AddDiscountProductDto {

    private String discountName;
    private String productName;

    @Builder
    public AddDiscountProductDto(String discountName, String productName) {
        this.discountName = discountName;
        this.productName = productName;
    }

}
