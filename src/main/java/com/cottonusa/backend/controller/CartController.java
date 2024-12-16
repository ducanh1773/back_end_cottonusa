package com.cottonusa.backend.controller;

import com.cottonusa.backend.DTO.CartItemDTO;
import com.cottonusa.backend.DTO.CartUpdateRequest;
import com.cottonusa.backend.DTO.UpdateQuantityRequest;
import com.cottonusa.backend.modal.*;
import com.cottonusa.backend.repository.CartItemRepository;
import com.cottonusa.backend.repository.CartRepository;
import com.cottonusa.backend.repository.CustomerRepository;
import com.cottonusa.backend.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import utility.JwtUtil;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final JwtUtil jwtUtil ;// Constructor Injection


    @Autowired
    public CartController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
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

@PostMapping("/add/{productId}")
public ResponseEntity<String> themSanPhamVaoGioHang(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long productId,
        @RequestParam int quantity,
        @RequestParam long sizeAttributeId,
        @RequestParam long colorAttributeId) {
    System.out.println("Authorization Header: " + authHeader);
    if (productId == null) {
        return ResponseEntity.badRequest().body("ID sản phẩm không hợp lệ");
    }

    // Validate input
    if (sizeAttributeId <= 0 || colorAttributeId <= 0) {
        return ResponseEntity.badRequest().body("Invalid size or color attribute ID");
    }

    // Lấy email từ token
    String token = authHeader.replace("Bearer ", "");
    String email = JwtUtil.getEmailFromToken(token); // Lấy email từ token
    System.out.println("Email: " + email);

    if (email == null) {
        return ResponseEntity.badRequest().body("Email không hợp lệ");
    }

    // Tìm khách hàng dựa trên email
    Customer customer = customerRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

    // Tìm sản phẩm
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

    // Tìm giỏ hàng của khách hàng hoặc tạo mới
    Cart gioHang = cartRepository.findByCustomerId(customer.getId())
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setCustomer(customer);
                newCart.setTotalPrice(0.0);
                return cartRepository.save(newCart);
            });

    // Kiểm tra sản phẩm có trong giỏ hàng hay chưa
    Optional<CartItem> existingItem = gioHang.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId) &&
                    item.getSizeAttributeId() == sizeAttributeId &&
                    item.getColorAttributeId() == colorAttributeId)
            .findFirst();

    if (existingItem.isPresent()) {
        // Cập nhật số lượng
        CartItem cartItem = existingItem.get();
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
        // Thêm sản phẩm mới vào giỏ hàng
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(gioHang);
        cartItem.setImg_product(product.getImg_product());
        cartItem.setSizeAttributeId(sizeAttributeId);
        cartItem.setColorAttributeId(colorAttributeId);
        cartItem.setPrice(product.getPrice());

        gioHang.getItems().add(cartItem);
    }

    // Cập nhật tổng giá
    gioHang.setTotalPrice(
            gioHang.getItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum()
    );

    // Lưu giỏ hàng
    System.out.println("Authorization Header: " + authHeader);
    System.out.println("Token: " + token);
    System.out.println("Email from token: " + email);
    cartRepository.save(gioHang);
    return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
}
@PutMapping("/{cartId}/updateQuantity")
public ResponseEntity<String> updateProductQuantity(
        @PathVariable Long cartId,
        @RequestBody UpdateQuantityRequest request) {

    // Find the cart by ID
    Optional<Cart> cartOptional = cartRepository.findById(cartId);
    if (cartOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
    }

    // Find the cart item by product ID within the cart
    Cart cart = cartOptional.get();
    Optional<CartItem> cartItemOptional = cartItemRepository.findByCartAndProductId(cart, request.getProductId());

    if (cartItemOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart");
    }

    // Update quantity and save the cart item
    CartItem cartItem = cartItemOptional.get();
    cartItem.setQuantity(request.getQuantity());
    cartItemRepository.save(cartItem);

    return ResponseEntity.ok("Quantity updated successfully");
}
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<String> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        // Tìm sản phẩm trong giỏ hàng
        Optional<CartItem> cartItemOpt = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (cartItemOpt.isPresent()) {
            // Xóa sản phẩm khỏi giỏ hàng
            cartItemRepository.delete(cartItemOpt.get());
            return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại trong giỏ hàng");
        }
    }
//    @PostMapping("/place-order")
//    public ResponseEntity<String> placeOrder(@RequestBody Cart cart, HttpServletRequest request) {
//        try {
//            // Lấy token từ header Authorization
//            String authHeader = request.getHeader("Authorization");
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return ResponseEntity.status(401).body("Token không hợp lệ.");
//            }
//
//            // Lấy customerId từ token
//            String token = authHeader.substring(7);
//            String customerId = jwtUtil.getUserIdFromToken(token);
//
//            // Lưu customerId vào session
//            HttpSession session = request.getSession();
//            session.setAttribute("id", customerId);
//
//            // Lấy Customer từ customerId
//            Optional<Customer> customerOptional = customerRepository.findById(Long.parseLong(customerId));
//            Customer customer = customerOptional.orElseThrow(() -> new RuntimeException("Customer không tồn tại"));
//
//            // Gán Customer vào Cart
//            cart.setCustomer(customer);
//
//            // Lưu đơn hàng vào cơ sở dữ liệu
//            cartRepository.save(cart);
//            return ResponseEntity.ok("Đặt hàng thành công!");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Lỗi khi đặt hàng.");
//        }
//    }
        @PostMapping("/shipping/calculate")
        public ResponseEntity<Map<String, Object>> calculateShipping(@RequestBody Map<String, String> request) {
            try {
                String address = request.get("address");

                // Kiểm tra xem địa chỉ có được gửi từ client hay không
                if (address == null || address.isEmpty()) {
                    return ResponseEntity.badRequest().body(Map.of(
                            "error", "Địa chỉ không được để trống."
                    ));
                }

                // Trả về phí ship mặc định
                int shippingFee = 30000; // 30,000 VND

                return ResponseEntity.ok(Map.of(
                        "address", address,
                        "shippingFee", shippingFee
                ));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(Map.of(
                        "error", "Đã xảy ra lỗi khi tính phí ship."
                ));
            }
        }




}
