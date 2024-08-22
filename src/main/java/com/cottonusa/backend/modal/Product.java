package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity

public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String nameProduct;
    @Getter @Setter
    private long priceProduct;
    @Getter @Setter
    private String summary;
    @Getter @Setter
    private String cover;
    @Getter @Setter
    private String sku;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private String category_id;
    @Getter @Setter
    private Date created_at;
    @Getter @Setter
    private Date deleted_at;
    protected Product() {}


    @Override
    public String toString() {
        return String.format(
                "Product[id=%d]",
                id);
    }



}
