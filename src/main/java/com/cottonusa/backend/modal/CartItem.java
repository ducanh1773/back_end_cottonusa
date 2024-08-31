package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long cart_id;
    private Long product_id;
    private Long products_sku_id;
    private Long quantity;
    private Date created_at;
    private Date deleted_at;

}
