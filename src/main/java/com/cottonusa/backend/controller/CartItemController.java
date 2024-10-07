package com.cottonusa.backend.controller;

import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cartController")
public class CartItemController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @GetMapping("/{cartId}/items")
    public List<CartItem> getCartItems(@PathVariable Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

}
