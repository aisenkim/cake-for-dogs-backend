package com.cakefordogs.cakefordogs.product.dto;

import com.cakefordogs.cakefordogs.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductSaveRequestDto {

    private String name;
    private String description;
    private BigDecimal price;

    @Builder
    public ProductSaveRequestDto(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
