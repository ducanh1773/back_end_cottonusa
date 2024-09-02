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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public void setCart_id(Long cart_id) {
        this.cart_id = cart_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProducts_sku_id() {
        return products_sku_id;
    }

    public void setProducts_sku_id(Long products_sku_id) {
        this.products_sku_id = products_sku_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public CartItem(Long id, Long cart_id, Long product_id, Long products_sku_id, Long quantity, Date created_at, Date deleted_at) {
        this.id = id;
        this.cart_id = cart_id;
        this.product_id = product_id;
        this.products_sku_id = products_sku_id;
        this.quantity = quantity;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public CartItem() {
    }

    public void setCart(Cart cart) {
    }

    public void setProduct(Product product) {
    }
}
