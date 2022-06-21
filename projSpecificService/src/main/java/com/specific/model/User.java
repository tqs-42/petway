package com.specific.model;

import lombok.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Size(min = 8)
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    public User(@Email String email, @Size(min = 8) String password, String fullname2) {
        this.email = email;
        this.password = password;
        this.fullname = fullname2;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", fullname=" + fullname + ", password=" + password + "]";
    }

    public User orElseThrow(Object object) {
        return null;
    }
    
}
