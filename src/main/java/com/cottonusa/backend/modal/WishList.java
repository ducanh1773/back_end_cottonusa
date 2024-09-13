package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long product_id;
    private Long user_id;
    private Date created_at;
    private Date deleted_at;

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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public WishList(Long id, Long product_id, Long user_id, Date created_at, Date deleted_at) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public WishList() {
    }
}
