package com.cottonusa.backend.controller;

import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Order;
import com.cottonusa.backend.modal.OrderDetail;
import com.cottonusa.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import utility.JwtUtil;

import java.time.LocalDateTime;
import java.util.*;


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
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestHeader("Authorization") String authHeader, @RequestBody Order orderRequest) {
        try {
            // Lấy token từ header
            String token = authHeader.replace("Bearer ", "");

            // Lấy email từ token
            String email = JwtUtil.getEmailFromToken(token);
            if (email == null) {
                return ResponseEntity.badRequest().body("Email không hợp lệ");
            }

            // Tìm khách hàng dựa trên email
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            // Tạo một đơn hàng mới
            Order order = new Order();
            order.setCreatedAt(new Date());
            order.setOrderStatus("Pending");
            order.setPaymentMethod(orderRequest.getPaymentMethod());
            order.setShippingAddress(orderRequest.getShippingAddress());
            order.setTotalPrice(orderRequest.getTotalPrice());
            order.setUserId(customer.getId()); // Sử dụng userId từ khách hàng


            System.out.println("Payment Method: " + orderRequest.getPaymentMethod());
            System.out.println("Shipping Address: " + orderRequest.getShippingAddress());
            System.out.println("Total Price: " + orderRequest.getTotalPrice());
            System.out.println("Order Request: " + orderRequest.toString());

            if (orderRequest.getOrderDetails() == null) {
                return ResponseEntity.badRequest().body("Chi tiết đơn hàng không được để trống.");
            }
//            if (orderRequest.getCartId() != null) {
//                order.setCartId(orderRequest.getCartId()); // Set the cart_id in the order
//            }

            // Tạo các chi tiết đơn hàng từ orderRequest
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (OrderDetail detail : orderRequest.getOrderDetails()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPrice(detail.getPrice());
                orderDetail.setProductId(detail.getProductId());
                orderDetail.setQuantity(detail.getQuantity());
                orderDetail.setTotal(detail.getTotal());
                orderDetail.setOrder(order); // Liên kết chi tiết với đơn hàng
                orderDetails.add(orderDetail); // Thêm vào danh sách
            }


            order.setOrderDetails(orderDetails); // Thiết lập chi tiết đơn hàng trong Order
            orderRepository.save(order); // Lưu đơn hàng với các chi tiết

            // Xóa giỏ hàng của người dùng
//            cartRepository.deleteByCustomerId(customer.getId());
//            cartItemRepository.deleteByCartId(orderRequest.getCartId());

            return ResponseEntity.status(201).body(order); // Trả về đơn hàng đã tạo
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getOrderDetails(@RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token and email
            String token = authHeader.replace("Bearer ", "");
            String email = JwtUtil.getEmailFromToken(token);
            if (email == null) {
                return ResponseEntity.badRequest().body("Email không hợp lệ");
            }

            // Find customer
            Customer customer = customerRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

            Long userId = customer.getId();

            // Fetch order IDs
            List<Long> orderIds = orderDetailRepository.findLatestOrderIdsByUserId(userId);
            if (orderIds.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Fetch product IDs for each order
            Map<Long, List<Long>> productDetailMap = new HashMap<>();
            for (Long orderId : orderIds) {
                List<Long> productIds = orderDetailRepository.findProductIdsByOrderId(orderId);
                productDetailMap.put(orderId, productIds);
            }

            return ResponseEntity.ok(productDetailMap); // Return orderId -> productIds mapping

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi lấy thông tin đơn hàng: " + e.getMessage());
        }
    }




}
