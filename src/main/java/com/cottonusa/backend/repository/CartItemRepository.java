package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
