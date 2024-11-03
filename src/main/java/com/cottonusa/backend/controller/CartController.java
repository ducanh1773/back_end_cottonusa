package com.cottonusa.backend.controller;

import com.cottonusa.backend.DTO.CartItemDTO;
import com.cottonusa.backend.DTO.CartUpdateRequest;
import com.cottonusa.backend.DTO.UpdateQuantityRequest;
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
import org.springframework.web.bind.annotation.*;
import java.util.List;
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

//    @Autowired
//    private Cart cart;

//    @GetMapping("/{customerId}")
//    public ResponseEntity<Cart> xemGioHang(@PathVariable Long customerId) {
//        Cart gioHang = cartRepository.findByCustomerId(customerId)
//                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại cho khách hàng này"));
//        return ResponseEntity.ok(gioHang);
//    }

    @GetMapping("/{customerId}/product-ids")
    public ResponseEntity<List<Long>> layIdSanPhamTuGioHang(@PathVariable Long customerId) {
        // Tìm giỏ hàng dựa trên customerId
        Cart gioHang = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại cho khách hàng này"));

        // Lấy danh sách ID sản phẩm từ các mục trong giỏ hàng
        List<Long> productIds = gioHang.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());

        return ResponseEntity.ok(productIds);
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

//    @PostMapping("/{customerId}/add/{productId}")
//    public ResponseEntity<String> themSanPhamVaoGioHang(
//            @PathVariable Long customerId,
//            @PathVariable Long productId,
//            @RequestParam int quantity) {
//
//        // Tìm khách hàng
//        Customer customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
//
//        // Tìm sản phẩm
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
//
//        // Tìm giỏ hàng của khách hàng hoặc tạo mới
//        Cart gioHang = cartRepository.findByCustomerId(customerId)
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setCustomer(customer);
//                    newCart.setTotalPrice(0.0);
//                    return cartRepository.save(newCart);
//                });
//
//        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
//        Optional<CartItem> existingItem = gioHang.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst();
//
//        if (existingItem.isPresent()) {
//            // Nếu sản phẩm đã có, cập nhật số lượng
//            CartItem cartItem = existingItem.get();
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        } else {
//            // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setCart(gioHang);
//            cartItem.setImg_product(product.getImg_product());
//
//
//            gioHang.getItems().add(cartItem);
//        }
//
//        // Cập nhật tổng giá của giỏ hàng
//        gioHang.setTotalPrice(
//                gioHang.getItems().stream()
//                        .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
//                        .sum()
//        );
//
//        // Lưu giỏ hàng và các thay đổi
//        cartRepository.save(gioHang);
//
//        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
//    }
@PostMapping("/{customerId}/add/{productId}")
public ResponseEntity<String> themSanPhamVaoGioHang(
        @PathVariable Long customerId,
        @PathVariable Long productId,
        @RequestParam int quantity,
        @RequestParam long sizeAttributeId,    // New size attribute parameter
        @RequestParam long colorAttributeId) { // New color attribute parameter

    // Tìm khách hàng
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));

    // Tìm sản phẩm
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

    // Tìm giỏ hàng của khách hàng hoặc tạo mới
    Cart gioHang = cartRepository.findByCustomerId(customerId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setCustomer(customer);
                newCart.setTotalPrice(0.0);
                return cartRepository.save(newCart);
            });

    // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
    Optional<CartItem> existingItem = gioHang.getItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId) &&
                    item.getSizeAttributeId() == sizeAttributeId &&
                    item.getColorAttributeId() == colorAttributeId)  // Check if size and color match
            .findFirst();

    if (existingItem.isPresent()) {
        // Nếu sản phẩm đã có, cập nhật số lượng
        CartItem cartItem = existingItem.get();
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
        // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setCart(gioHang);
        cartItem.setImg_product(product.getImg_product());
        cartItem.setSizeAttributeId(sizeAttributeId);   // Set size attribute
        cartItem.setColorAttributeId(colorAttributeId);
        cartItem.setPrice(product.getPrice());// Set color attribute

        gioHang.getItems().add(cartItem);
    }

    // Cập nhật tổng giá của giỏ hàng
    gioHang.setTotalPrice(
            gioHang.getItems().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum()
    );

    // Lưu giỏ hàng và các thay đổi
    cartRepository.save(gioHang);

    return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
}
    public ResponseEntity<List<Long>> layIdSanPhamTuCart(@PathVariable Long cartId) {
        // Tìm giỏ hàng dựa trên cartId
        Cart gioHang = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));

        // Lấy danh sách ID sản phẩm từ các mục trong giỏ hàng
        List<Long> productIds = gioHang.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());

        return ResponseEntity.ok(productIds);
    }
//    @PutMapping("/{cartId}/updateQuantity")
//    public ResponseEntity<String> updateProductQuantity(
//            @PathVariable("cartId") Long cartId, // Correct usage of @PathVariable
//            @RequestBody UpdateQuantityRequest request) {
//        // Implement your logic to update the product quantity in the database
//        // For example:
//        // cartService.updateQuantity(cartId, request.getProductId(), request.getQuantity());
//
//        // Return a success response
//        return ResponseEntity.ok("Quantity updated successfully");
//    }
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

}
