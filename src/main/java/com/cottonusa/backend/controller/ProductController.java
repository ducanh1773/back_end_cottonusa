package com.cottonusa.backend.controller;


import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class ProductController {
    private final ProductRepository repository;
    private List<Product> products;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public List<Product> getProductLimit(

            @RequestParam(required = false, defaultValue = "100") int limit) {

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

    @GetMapping("/products/FindProduct")
    public Product findProductByID(@RequestBody Product product){
        return product;
    }

    @GetMapping("products/findProduct/{id}")
    public ResponseEntity<Product> findProductByID(@PathVariable Long id) {
        Optional<Product> product = repository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}
