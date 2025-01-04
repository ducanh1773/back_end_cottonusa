package com.cottonusa.backend.controller;

import com.cottonusa.backend.modal.Order;
import com.cottonusa.backend.modal.OrderDetail;
import com.cottonusa.backend.repository.CartItemRepository;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.OrderDetailRepository;
import com.cottonusa.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDateTime;
import java.util.UUID;
import  java.util.List;
import  java.util.ArrayList;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order orderRequest) {
        try {
            // Tạo một đơn hàng mới
            Order order = new Order();
            order.setCreatedAt(null);
            order.setOrderStatus("Pending");
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setShippingAddress(orderRequest.getShippingAddress());
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setUserId(orderRequest.getUserId());

            // Initialize order details list if not done in the Order class
            if (orderRequest.getOrderDetails() == null) {
                return ResponseEntity.badRequest().body("Chi tiết đơn hàng không được để trống.");
            }

            // Tạo các chi tiết đơn hàng từ orderRequest
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (OrderDetail detail : orderRequest.getOrderDetails()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPrice(detail.getPrice());
                orderDetail.setProductId(detail.getProductId());
                orderDetail.setQuantity(detail.getQuantity());
                orderDetail.setTotal(detail.getTotal());
                orderDetail.setOrder(order); // Liên kết chi tiết với đơn hàng
                orderDetails.add(orderDetail); // Add to the list
            }

            order.setOrderDetails(orderDetails); // Set the order details in the Order
            orderRepository.save(order); // Save the Order with its details

            // Xóa giỏ hàng của người dùng
            cartRepository.deleteByCustomerId(orderRequest.getUserId());
            cartItemRepository.deleteByCartId(orderRequest.getCartId());

            return ResponseEntity.status(201).body(order); // Trả về đơn hàng đã tạo
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }


}
