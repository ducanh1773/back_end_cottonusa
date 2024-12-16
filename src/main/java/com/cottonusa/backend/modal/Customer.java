package com.cottonusa.backend.modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity

public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String passWord;

    private String token;



    protected Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public Customer(String lastName, String firstName, Long id, String phoneNumber, int age, String email , String passWord) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
        this.email = email;
        this.passWord = passWord;
    }



    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s',email='%s',passWord='%s']",
                id, firstName,lastName,email,passWord);
    }



}