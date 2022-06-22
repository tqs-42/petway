package com.specific.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "STORE")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId", nullable = true)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "active", nullable = true)
    private Boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Manager manager;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    Set<Product> products;

    public void setId(long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Store(String name, String address, Boolean active) {
        this.name = name;
        this.address = address;
        this.active = active;
        this.products = new HashSet<>();
    }

    public Store() {
        this.products = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
