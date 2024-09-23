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

    public String getImg_product() {
        return img_product;
    }

    public void setImg_product(String img_product) {
        this.img_product = img_product;
    }

    private String img_product;
    protected Product() {}

    public Product(Long id, String nameProduct, long priceProduct, String summary, String cover, String sku, String description, String category_id, Date created_at, Date deleted_at, String img_product) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.summary = summary;
        this.cover = cover;
        this.sku = sku;
        this.description = description;
        this.category_id = category_id;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.img_product = img_product;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%d]",
                id);
    }


    public double getPrice() {
        return priceProduct;
    }
}
