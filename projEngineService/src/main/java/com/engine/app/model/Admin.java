package com.engine.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Person {

    public Admin(String email, String address, String fullname, String password) {
        super(email, address, fullname, password);
    }

    public Admin() {
    }
    
}
