package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {


    List<OrderDetail> findByOrderId(Long orderId);
    @Query("SELECT od.order.id FROM OrderDetail od WHERE od.order.userId = :userId ORDER BY od.order.createdAt DESC")
    List<Long> findOrderIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT od.order.id FROM OrderDetail od WHERE od.order.userId = :userId ORDER BY od.order.createdAt DESC LIMIT 1")
    List<Long> findLatestOrderIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT od.id FROM OrderDetail od WHERE od.order.id = :orderId")
    List<Long> findOrderDetailIdsByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT od.productId FROM OrderDetail od WHERE od.order.id = :orderId")
    List<Long> findProductIdsByOrderId(@Param("orderId") Long orderId);
}
