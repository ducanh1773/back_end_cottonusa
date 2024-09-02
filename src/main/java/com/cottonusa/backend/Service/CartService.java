//package com.cottonusa.backend.Service;
//
//import com.cottonusa.backend.modal.Cart;
//import com.cottonusa.backend.modal.CartItem;
//import com.cottonusa.backend.modal.Product;
//import com.cottonusa.backend.repository.CartItemRepository;
//import com.cottonusa.backend.repository.CartRepository;
//import com.cottonusa.backend.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Service
//public class CartService {
//    @Autowired
//    private CartRepository cartRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
//        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(cart);
//        cartItem.setProduct(product);
//        cartItem.setQuantity((long) quantity);
//
//        cart.getItems().add(cartItem);
//        return cartRepository.save(cart);
//    }
//
//    public Cart removeItemFromCart(Long cartId, Long cartItemId) {
//        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
//
//        cart.getItems().remove(cartItem);
//        cartItemRepository.delete(cartItem);
//        return cartRepository.save(cart);
//    }
//
//    public Cart getCart(Long userId) {
//        return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
//    }
//}