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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client extends User {

    @Column(name = "address", nullable = false)
    private String address;

    // @OneToMany(mappedBy = "client")
    // Set<UserAddress> addresses;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    public Client(String email, String password, String fullname, String address) {
        super(email, password, fullname);
        this.address = address;
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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
