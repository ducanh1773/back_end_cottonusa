package com.cottonusa.backend.controller;

import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.CartItemRepository;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity((long) quantity);


        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }

}
