package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
