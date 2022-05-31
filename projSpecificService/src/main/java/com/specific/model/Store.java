package com.specific.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "STORE")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storeId", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy="store")
    Set<User> managers;

    @OneToMany(mappedBy="store")
    Set<Product> products;

    public Store(String name, String address, Boolean active, Set<User> managers, Set<Product> products) {
        this.name = name;
        this.address = address;
        this.active = active;
        this.managers = managers;
        this.products = products;
    }
    
}
