package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
