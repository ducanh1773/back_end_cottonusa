package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Long parent_id;
    private String name;
    private String description;
    private Date created_at;
    private Date deleted_at;

    public SubCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public SubCategory(Long id, Long parent_id, String name, String description, Date created_at, Date deleted_at) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.description = description;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }
}
