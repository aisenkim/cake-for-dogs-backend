package com.cakefordogs.cakefordogs.cartItem;

import com.cakefordogs.cakefordogs.cartItem.dto.SaveCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping
    public List<CartItem> getCartItems() {
        return cartItemService.getCartItems();
    }

    @PostMapping
    public void addCartItem(@RequestBody SaveCartItemDto saveCartItemDto) {
        cartItemService.addCartItem(saveCartItemDto);
    }
}
