package com.cakefordogs.cakefordogs.cartItem.dto;

import com.cakefordogs.cakefordogs.cartItem.CartItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveCartItemDto {

    private Long productId;
    private Long quantity;

    @Builder
    public SaveCartItemDto(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

//    public void toEntity() {
//    }


}
