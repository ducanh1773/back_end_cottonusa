package com.cottonusa.backend.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String description;
    @Getter @Setter
    private String category_id;
    @Getter @Setter
    private Date created_at;
    @Getter @Setter
    private Date deleted_at;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductsSku> skus;

    public List<ProductsSku> getSkus() {
        return skus;
    }

    public void setSkus(List<ProductsSku> skus) {
        this.skus = skus;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(long priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
