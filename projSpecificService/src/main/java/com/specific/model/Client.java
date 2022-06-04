package com.specific.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "password", nullable = false)
    private String password;

    // @OneToMany(mappedBy = "client")
    // Set<UserAddress> addresses;

    // @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY)
    // private Cart cart;

    public Client(String email, String address, String fullname, String password) {
        this.email = email;
        this.address = address;
        this.fullname = fullname;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // public Client(String email, String password, String fullname,
    // Set<UserAddress> addresses) {
    // super(email, password, fullname);
    // this.addresses = addresses;
    // }

    // public Set<UserAddress> getAddresses() {
    // return addresses;
    // }

    // public void setAddresses(Set<UserAddress> addresses) {
    // this.addresses = addresses;
    // }

    // public Cart getCart() {
    // return cart;
    // }

    // public void setCart(Cart cart) {
    // this.cart = cart;
    // }

}
