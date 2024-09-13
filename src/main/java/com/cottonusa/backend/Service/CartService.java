package com.cottonusa.backend.Service;
import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart addCartItem(Long cartId, Long productId, long quantity) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                CartItem cartItem = new CartItem(product, quantity, cart);
                cart.addCartItem(cartItem);
                cartRepository.save(cart);
            }
        }
        return cart;
    }

    public Cart createCart(Long userId) {
        Cart cart = new Cart(userId);
        return cartRepository.save(cart);
    }

    public void removeCartItem(Long cartId, Long cartItemId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
            cart.updateTotal();
            cartRepository.save(cart);
        }
    }
}
