package com.cottonusa.backend.controller;

import com.cottonusa.backend.DTO.CartItemDTO;
import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.CartItem;
import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.CartItemRepository;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.CustomerRepository;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> xemGioHang(@PathVariable Long customerId) {
        Cart gioHang = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại cho khách hàng này"));
        return ResponseEntity.ok(gioHang);
    }


    @PostMapping("/create/{customerId}")
    public ResponseEntity<?> createCartForCustomer(@PathVariable Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));

            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setTotalPrice(0.0);

            Cart savedCart = cartRepository.save(cart);

            return ResponseEntity.ok(savedCart);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating cart: " + e.getMessage());
        }

    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<String> xoaSanPhamKhoiGio(@PathVariable Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok("Đã xóa sản phẩm khỏi giỏ");
    }

    @PutMapping("/update")
    public ResponseEntity<String> capNhatSoLuongSanPham(@RequestBody CartItemDTO cartItemDTO) {
        CartItem chiTietGio = cartItemRepository.findById(cartItemDTO.getId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        chiTietGio.setQuantity(cartItemDTO.getQuantity());
        chiTietGio.getCart().setTotalPrice(
                chiTietGio.getCart().getTotalPrice() +
                        chiTietGio.getProduct().getPrice() * (cartItemDTO.getQuantity() - chiTietGio.getQuantity())
        );

        cartItemRepository.save(chiTietGio);

        return ResponseEntity.ok("Đã cập nhật số lượng sản phẩm trong giỏ");
    }

    @PostMapping("/them")
    public ResponseEntity<String> themSanPhamVaoGio(@RequestBody CartItemDTO cartItemDTO) {
        // Tìm giỏ hàng theo customerId
        Cart gioHang = cartRepository.findByCustomerId(cartItemDTO.getCustomerId())
                .orElse(new Cart()); // Tạo giỏ hàng mới nếu không có

        // Nếu giỏ hàng mới, thêm thông tin khách hàng
        if (gioHang.getId() == null) {
            Customer khachHang = customerRepository.findById(cartItemDTO.getCustomerId())
                    .orExlseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            gioHang.setCustomer(khachHang);
            gioHang.setItems(new ArrayList<>());
        }

        // Tìm sản phẩm theo productId
        Product sanPham = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Tạo CartItem và thêm vào giỏ hàng
        CartItem chiTietGio = new CartItem();
        chiTietGio.setCart(gioHang);
        chiTietGio.setProduct(sanPham);
        chiTietGio.setQuantity(cartItemDTO.getQuantity());

        gioHang.getItems().add(chiTietGio);
        gioHang.setTotalPrice(gioHang.getTotalPrice() + sanPham.getPrice() * chiTietGio.getQuantity());

        // Lưu giỏ hàng
        cartRepository.save(gioHang);

        return ResponseEntity.ok("Đã thêm sản phẩm vào giỏ");
    }





}
