package com.specific.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "MANAGER")
public class Manager extends User{

    @ManyToOne
    @JoinColumn(name = "storeId", nullable = true)
    private Store store = null;

    public Manager(@Email String email, @Size(min = 8) String password, String fullname, Store store) {
        super(email, password, fullname);
        this.store = store;
    }

    public Manager() {
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
    
}
