package com.cottonusa.backend.controller;

import com.cottonusa.backend.Service.CartService;
import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.CartItemRepository;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Tạo giỏ hàng mới
    @PostMapping("/create")
    public Cart createCart(@RequestParam Long userId) {
        return cartService.createCart(userId);
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/{cartId}/add")
    public Cart addCartItem(@PathVariable Long cartId, @RequestParam Long productId, @RequestParam long quantity) {
        return cartService.addCartItem(cartId, productId, quantity);
    }

    // Lấy giỏ hàng theo ID
    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/{cartId}/remove/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        cartService.removeCartItem(cartId, cartItemId);
    }
}
