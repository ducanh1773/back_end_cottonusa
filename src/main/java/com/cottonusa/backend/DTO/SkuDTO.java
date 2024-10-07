package com.cottonusa.backend.DTO;

public class SkuDTO {
    private long size_attribute_id;
    private long color_attribute_id;
    private String sku;
    private String price;
    private long quantity;

    public SkuDTO(long size_attribute_id, long color_attribute_id, String sku, String price, long quantity) {
        this.size_attribute_id = size_attribute_id;
        this.color_attribute_id = color_attribute_id;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    public SkuDTO() {
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
}
