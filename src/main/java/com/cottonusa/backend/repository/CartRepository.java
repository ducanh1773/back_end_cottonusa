package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CartRepository extends  JpaRepository<Cart, Long> {

}
