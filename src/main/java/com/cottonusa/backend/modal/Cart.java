package com.cottonusa.backend.modal;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private double totalPrice;


//    public void addItem(Product product, int quantity) {
//        for (CartItem item : items) {
//            if (item.getProduct().getId().equals(product.getId())) {
//                item.setQuantity(item.getQuantity() + quantity);
//                return;
//            }
//        }
//        items.add(new CartItem(product, quantity));
//    }


    // Constructors
    public Cart() {}

    public Cart(Customer customer) {
        this.customer = customer;
    }

    // Method to calculate total price
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    // Method to add items to the cart
    public void addItem(CartItem item) {
        items.add(item);
        calculateTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Cart(Long id, Customer customer, List<CartItem> items, double totalPrice) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.totalPrice = totalPrice;
    }



    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
