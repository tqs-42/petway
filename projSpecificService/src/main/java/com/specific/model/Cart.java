package com.specific.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CART")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId", nullable = false)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Client client;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    Set<RequestProducts> products;

    @OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    public Cart(Client client, Set<RequestProducts> products, Request request) {
        this.client = client;
        this.products = products;
        this.request = request;
    }

    public Cart(Client client) {
        this.client = client;
        products = new HashSet<>();
    }

    public Cart() {
        products = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<RequestProducts> getProducts() {
        return products;
    }

    public void setProducts(Set<RequestProducts> products) {
        this.products = products;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
