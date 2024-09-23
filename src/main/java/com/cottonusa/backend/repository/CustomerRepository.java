package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
    Optional<Customer> findByEmail(String email);

    List<Customer> findByFirstName(String firstName);
}