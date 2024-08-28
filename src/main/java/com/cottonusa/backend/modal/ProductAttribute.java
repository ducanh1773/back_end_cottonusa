package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String value;
    private Date created_at;
    private Date deleted_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public ProductAttribute(long id, String value, Date created_at, Date deleted_at) {
        this.id = id;
        this.value = value;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public ProductAttribute() {
    }
}
