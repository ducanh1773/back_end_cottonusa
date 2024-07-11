package com.cottonusa.backend.controller;


import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class ProductController {
    private final ProductRepository repository;
    private List<Product> products;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public List<Product> getProductLimit(

            @RequestParam(required = false, defaultValue = "5") int limit) {

        List<Product> products = repository.findAll();


        if (products.isEmpty()) {
            return new ArrayList<>();
        } else {
            return products.subList(0, Math.min(products.size(), limit));
        }
    }

    @PostMapping("/products/create-product")
    public Product creatProduct(@RequestBody Product product) {
        Product savedProduct = repository.save(product);
        return product;


    }
}
