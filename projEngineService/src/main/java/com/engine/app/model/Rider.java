package com.engine.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rider")
@Inheritance(strategy=InheritanceType.JOINED)
public class Rider extends Person {

    @Column(name = "isActive", nullable = true)
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JoinColumn(name = "review_id")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @JoinColumn(name = "delivery_id")
    private List<Delivery> deliveries = new ArrayList<>();

    public Rider(String email, String address, String fullname, String password, Boolean isActive) {
        super(email, address, fullname, password);
        this.isActive = isActive;
    }

    public Rider(String email, String address, String fullname, String password) {
        super(email, address, fullname, password);
    }

    public Rider(String email, String address, String fullname) {
        super(email, address, fullname);
    }

    public Rider() {
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }



    @Override
    public String toString() {
        return "Rider " + super.toString() + " [deliveries=" + deliveries + ", isActive=" + isActive + ", reviews=" + reviews + "]";
    }
    
    
    
}
