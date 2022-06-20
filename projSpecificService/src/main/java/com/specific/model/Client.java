package com.specific.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CLIENT")
public class Client extends User {

    @Column(name = "address", nullable = true)
    private String address;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch =
    FetchType.LAZY)
    @JsonIgnore
    private Cart cart;

    public Client(String email, String password, String fullname, String address) {
        super(email, password, fullname);
        this.address = address;
        this.cart = new Cart(this);
    }

    public Client(){
        this.cart = new Cart(this);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
