package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT DISTINCT od.orderId FROM OrderDetail od WHERE od.order.userId = :userId AND   ")
    List<Long> findOrderIdsByUserId(@Param("userId") Long userId);
    List<OrderDetail> findByOrderId(Long orderId);
    @Query("SELECT od.orderId FROM OrderDetail od WHERE od.order.userId = :userId order by created_at desc limit 1")
    Long findLatestOrderIdByUserId(@Param("userId") Long userId);
}
