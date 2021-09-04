package com.cakefordogs.cakefordogs.discount.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class updateDiscountDto {
    private String name;
    private String description;
    private BigDecimal discountPercent;

    @Builder
    public updateDiscountDto(String name, String description, BigDecimal discountPercent) {
        this.name = name;
        this.description = description;
        this.discountPercent = discountPercent;
    }

}
