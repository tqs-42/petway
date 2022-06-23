package com.engine.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "persons")
public class Person implements Serializable{

    @Id
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Size(min = 8)
    @JsonIgnore 
    @Column(name = "password", nullable = false)
    private String password;

    public Person(String email, String address, String fullname, String password) {
        this.email = email;
        this.address = address;
        this.fullname = fullname;
        this.password = password;
    }

    public Person(String email, String address, String fullname) {
        this.email = email;
        this.address = address;
        this.fullname = fullname;
    }

    public Person() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Person [address=" + address + ", email=" + email + ", fullname=" + fullname + ", password=" + password
                + "]";
    }

    public Person orElseThrow(Object object) {
        return null;
    }

    

}