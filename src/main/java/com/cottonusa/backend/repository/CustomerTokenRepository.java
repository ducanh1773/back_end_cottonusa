package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.CustomerToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerTokenRepository {
    Optional<CustomerToken> findByUserId(Long userId);
}
