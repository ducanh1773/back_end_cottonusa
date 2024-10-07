//package com.cottonusa.backend.Service;
//
//import com.cottonusa.backend.modal.Cart;
//import com.cottonusa.backend.modal.CartItem;
//import com.cottonusa.backend.modal.Product;
//import com.cottonusa.backend.repository.CartItemRepository;
//import com.cottonusa.backend.repository.CartRepository;
//import com.cottonusa.backend.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CartItemService {
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    public Cart addToCart(Long customerId, Long productId, int quantity) {
//        Cart cart = cartRepository.findByCustomerId(customerId);
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst();
//
//        if (existingCartItem.isPresent()) {
//            CartItem cartItem = existingCartItem.get();
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        } else {
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setCart(cart);
//            cart.getCartItems().add(cartItem);
//        }
//
//        cartRepository.save(cart);
//        return cart;
//    }
//}
