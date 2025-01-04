package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem , Long> {
//    Optional<CartItem> findByCustomerId(Long customerId);
     List<CartItem> findByCartId(Long cartId);
     Optional<CartItem> findByCartAndProductId(Cart cart, Long productId);
     Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
     @Modifying // Indicates that this method modifies data
     @Transactional // Indicates that this method should be executed within a transaction
     @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId") // Custom query to delete items
     void deleteByCartId(@Param("cartId") Long cartId);

}
