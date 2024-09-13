package com.cottonusa.backend.controller;


import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    private CartItemRepository cartItemRepository;

    @GetMapping("/items")
    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    @PostMapping("/add")
    public CartItem addCartItem(@RequestBody CartItem cartItem) {

        return cartItemRepository.save(cartItem);
    }

    @DeleteMapping("/remove/{id}")
    public void removeCartItem(@PathVariable Long id) {
        cartItemRepository.deleteById(id);
    }
}