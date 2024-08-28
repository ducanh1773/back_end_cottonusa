package com.cottonusa.backend.controller;
import com.cottonusa.backend.DTO.CustomerDTO;
import com.cottonusa.backend.DTO.LoginRequest;
import com.cottonusa.backend.DTO.LoginResponse;
import com.cottonusa.backend.exception.CustomerNotFoundException;
import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.repository.CustomerRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utility.JwtUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
class CustomerController {


    private final CustomerRepository repository;
    private JwtUtil jwtUtil;

    CustomerController(
            CustomerRepository repository
    ) {
        this.repository = repository;
        this.jwtUtil = new JwtUtil();
    }



    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getFirstName() + " " + customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    @GetMapping("/customers")
    List<Customer> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/customers")
    CustomerDTO newCustomer(@RequestBody Customer newCustomer) {
        Optional<Customer> existingCustomer = repository.findByEmail(newCustomer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Email already exists!");
        }

        // Hash the password before saving
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        newCustomer.setPassWord(passwordEncoder.encode(newCustomer.getPassWord()));

        Customer savedCustomer = repository.save(newCustomer);
        return convertToDTO(savedCustomer);
    }


    // Single item

    @GetMapping("/customers/{id}")
    Customer one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());

                    // Hash the new password if it's being changed
                    if (!newCustomer.getPassWord().isEmpty()) {
                        customer.setPassWord(passwordEncoder.encode(newCustomer.getPassWord()));
                    }

                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setPassWord(passwordEncoder.encode(newCustomer.getPassWord()));
                    return repository.save(newCustomer);
                });
    }


    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/customers/first-name/{firstName}")
    public List<Customer> getCustomersByFirstName(@PathVariable String firstName) {
        return repository.findByFirstName(firstName);
    }

    @GetMapping("/customers/first-name-x/{firstName}")
    public List<Customer> getCustomersByFirstNameAndLimit(
            @PathVariable String firstName,
            @RequestParam(required = false, defaultValue = "5") int limit) {
        List<Customer> customers = repository.findByFirstName(firstName);


        if (customers.isEmpty()) {
            return new ArrayList<>();
        } else {
            return customers.subList(0, Math.min(customers.size(), limit));
        }
    }




    @CrossOrigin(origins = "http://localhost:3000" , allowCredentials = "true")
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest , HttpServletResponse response ) {
        Optional<Customer> customer = repository.findByEmail(loginRequest.getEmail());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (customer.isPresent() && passwordEncoder.matches(loginRequest.getPassWord(), customer.get().getPassWord())) {
            String token = jwtUtil.generateToken(loginRequest.getEmail());
            Cookie cookie = new Cookie("username", "JohnDoe");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 1 tuần
//            cookie.setSecure(true); // Chỉ gửi cookie qua HTTPS
//            cookie.setHttpOnly(true); // Không cho phép truy cập cookie từ JavaScript
            response.addCookie(cookie);
            response.setHeader("Set-Cookie",
                    "username=JohnDoe; Path=/; Max-Age=604800");
            return new LoginResponse(token, "Đăng nhập thành công!");
        } else {
            return new LoginResponse(null, "Email hoặc mật khẩu không đúng!");
        }
    }
}


