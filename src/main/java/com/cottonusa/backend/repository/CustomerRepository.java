package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Cart;
import com.cottonusa.backend.modal.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);

    List<Customer> findByFirstName(String firstName);
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.token = :token WHERE c.email = :email")
    void updateTokenByEmail(@Param("token") String token, @Param("email") String email);
}