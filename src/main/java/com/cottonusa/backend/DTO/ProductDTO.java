package com.cottonusa.backend.DTO;

import java.sql.Date;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String nameProduct;
    private long priceProduct;
    private String summary;
    private String cover;
    private String sku;
    private String description;
    private String category_id;
    private Date created_at;
    private Date deleted_at;
    private String img_product;
    private long sizeAttributeId;   // Add size attribute
    private long colorAttributeId;

    public String getImg_product() {
        return img_product;
    }

    public void setImg_product(String img_product) {
        this.img_product = img_product;
    }

    private List<SkuDTO> skus;

    public ProductDTO(Long id, String nameProduct, long priceProduct, String summary, String cover, String sku, String description, String category_id, Date created_at, Date deleted_at, String img_url, List<SkuDTO> skus) {
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
        this.skus = skus;
    }



    public ProductDTO(Long id, String nameProduct, long priceProduct, String summary, String cover, String sku, String description, String category_id, Date created_at, Date deleted_at, List<SkuDTO> skus) {
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
        this.skus = skus;
    }

    public ProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public long getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(long priceProduct) {
        this.priceProduct = priceProduct;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public List<SkuDTO> getSkus() {
        return skus;
    }

    public void setSkus(List<SkuDTO> skus) {
        this.skus = skus;
    }
}
