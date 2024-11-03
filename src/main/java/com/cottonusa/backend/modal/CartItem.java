package com.cottonusa.backend.modal;

import jakarta.persistence.*;
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private long sizeAttributeId;
    private long colorAttributeId;

    private int quantity;

    private String img_product;

    public String getImg_product() {
        return img_product;
    }

    public void setImg_product(String img_product) {
        this.img_product = img_product;
    }

    public CartItem(Long id, Cart cart, Product product, int quantity, String img_product, String size, String color, double price) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.img_product = img_product;

        this.price = price;
    }



    public long getSizeAttributeId() {
        return sizeAttributeId;
    }

    public void setSizeAttributeId(long sizeAttributeId) {
        this.sizeAttributeId = sizeAttributeId;
    }

    public long getColorAttributeId() {
        return colorAttributeId;
    }

    public void setColorAttributeId(long colorAttributeId) {
        this.colorAttributeId = colorAttributeId;
    }



    public double getPrice() {
        return price;
    }



    public void setPrice(double price) {
        this.price = price;
    }

    private double price;

    // Constructors
    public CartItem(Product product, int quantity) {}

    public CartItem(Long id, Cart cart, Product product, long sizeAttributeId, long colorAttributeId, int quantity, String img_product, double price) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.sizeAttributeId = sizeAttributeId;
        this.colorAttributeId = colorAttributeId;
        this.quantity = quantity;
        this.img_product = img_product;
        this.price = price;
    }

    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Long id, Cart cart, Product product, int quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
