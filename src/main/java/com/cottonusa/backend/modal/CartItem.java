package com.cottonusa.backend.modal;

import jakarta.persistence.*;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long product_id;
    private Long products_sku_id;
    private Long quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Constructors, getters, and setters
    public CartItem() {}

    public CartItem(Long product_id, Long products_sku_id, Long quantity, Date created_at, Date deleted_at, Cart cart, Product product) {
        this.product_id = product_id;
        this.products_sku_id = products_sku_id;
        this.quantity = quantity;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.cart = cart;
        this.product = product;
    }

    public CartItem(Product product, long quantity, Cart cart) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
// Other constructors, getters, setters, etc.
}

