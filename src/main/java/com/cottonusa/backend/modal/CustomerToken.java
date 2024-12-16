package com.cottonusa.backend.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
public class CustomerToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String token;

    @Column(nullable = false)
    private Long customerId;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    public CustomerToken(Long id, String token, Long customerId, LocalDateTime expirationDate) {
        this.id = id;
        this.token = token;
        this.customerId = customerId;
        this.expirationDate = expirationDate;
    }

    public CustomerToken() {
    }

//    public static Long getCustomerIdFromToken(String token) {
//        return customerId;
//    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
