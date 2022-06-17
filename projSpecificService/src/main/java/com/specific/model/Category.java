package com.specific.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoryId", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    Set<Product> products;

    public Category(String name) {
        this.name = name;
        this.products = new HashSet<Product>();
    }

    public Category() {

    }

    public void addToProducts(Product product) {
        products.add(product);
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return name;
    }

    public void setCategory(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category [name=" + name + ", id=" + id + ", products=" + "]";
    }

}
