package com.cottonusa.backend.controller;


import com.cottonusa.backend.exception.CustomerNotFoundException;
import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class CustomerController {

    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/customers")
    List<Customer> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newEmployee) {
        return repository.save(newEmployee);
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
}
