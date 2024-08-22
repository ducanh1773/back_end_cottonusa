package com.cottonusa.backend.modal;

import java.util.Date;

public class products_skus {
    private long id;
    private long product_id;
    private long size_attribute_id;
    private long color_attribute_id;
    private String sku;
    private String price;
    private long quantity;
    private Date created_at;
    private Date deleted_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getSize_attribute_id() {
        return size_attribute_id;
    }

    public void setSize_attribute_id(long size_attribute_id) {
        this.size_attribute_id = size_attribute_id;
    }

    public long getColor_attribute_id() {
        return color_attribute_id;
    }

    public void setColor_attribute_id(long color_attribute_id) {
        this.color_attribute_id = color_attribute_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
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

    public products_skus(long id, long product_id, long size_attribute_id, long color_attribute_id, String sku, String price, long quantity, Date created_at, Date deleted_at) {
        this.id = id;
        this.product_id = product_id;
        this.size_attribute_id = size_attribute_id;
        this.color_attribute_id = color_attribute_id;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public products_skus() {
    }
}
