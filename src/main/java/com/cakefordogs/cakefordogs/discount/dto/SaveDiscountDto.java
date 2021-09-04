package com.cakefordogs.cakefordogs.discount.dto;


import com.cakefordogs.cakefordogs.discount.Discount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class SaveDiscountDto {

    private String name;
    private String description;
    private BigDecimal discountPercent;

    @Builder
    public SaveDiscountDto(String name, String description, BigDecimal discountPercent) {
        this.name = name;
        this.description = description;
        this.discountPercent = discountPercent;
    }

    public Discount toEntity() {
        return Discount.builder()
                .name(name)
                .description(description)
                .discountPercent(discountPercent)
                .build();
    }

}
