package com.cakefordogs.cakefordogs.cartItem;

import com.cakefordogs.cakefordogs.cartItem.dto.SaveCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private CartItemRepository cartItemRepository;

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public void addCartItem(SaveCartItemDto saveCartItemDto) {
//        Optional<CartItem> cartItemOptional = cartItemRepository.findByCartItemName(saveCartItemDto.getProductId());
//
//        if (cartItemOptional.isPresent()) {
//            throw new BadRequestException("Cart Item already exists ");
//        }
//
//        cartItemOptional.save(saveCartItemDto)
//
    }
}
