package com.specific.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MANAGER")
public class Manager extends User{

    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL, fetch =
    FetchType.LAZY)
    private Store store;

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
