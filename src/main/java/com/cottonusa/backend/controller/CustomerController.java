package com.cottonusa.backend.controller;


import com.cottonusa.backend.DTO.CustomerDTO;
import com.cottonusa.backend.DTO.LoginRequest;
import com.cottonusa.backend.exception.CustomerNotFoundException;
import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
class CustomerController {


    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }



    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getFirstName()+ " "+ customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    @GetMapping("/customers")
    List<Customer> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/customers")
    CustomerDTO newCustomer(@RequestBody Customer newCustomer) {
        Optional<Customer> existingCustomer = repository.findByEmail(newCustomer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Email already exists!");
        }
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

        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
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
            @RequestParam(required = false, defaultValue = "10") int limit) {

        List<Customer> customers = repository.findByFirstName(firstName);


        if (customers.isEmpty()) {
            return new ArrayList<>();
        } else {
            return customers.subList(0, Math.min(customers.size(), limit));
        }
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Kiểm tra email và mật khẩu trong cơ sở dữ liệu
        Optional<Customer> customer = repository.findByEmail(loginRequest.getEmail());

        if (customer.isPresent() && customer.get().getPassWord().equals(loginRequest.getPassWord())) {
            return "Đăng nhập thành công!";
        } else {
            return "Email hoặc mật khẩu không đúng!";
        }
    }





}
