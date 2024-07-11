package com.cottonusa.backend.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

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
    private String imgProduct;



    protected Product() {}


    @Override
    public String toString() {
        return String.format(
                "Product[id=%d]",
                id);
    }

}
