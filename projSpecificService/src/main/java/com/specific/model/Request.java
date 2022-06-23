package com.specific.model;

<<<<<<< HEAD
=======
import javax.persistence.CascadeType;
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "REQUEST")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId", nullable = false)
    private long id;

    @Column(name = "riderId", nullable = true)
    private int riderId;

    @Column(name = "destinyAddress", nullable = false)
    private String destinyAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "cartId")
    private Cart cart;

    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RequestEvents requestEvents;

    public Request(int riderId, String destinyAddress, Cart cart) {
        this.riderId = riderId;
        this.destinyAddress = destinyAddress;
        this.cart = cart;
    }

    public Request(String destinyString, Cart cart){
        this.cart = cart;
        this.destinyAddress = destinyString;
    }

    public Request() {

    }

    public long getId() {
        return id;
    }

    public int getRiderId() {
        return riderId;
    }

    public void setRiderId(int riderId) {
        this.riderId = riderId;
    }

    public String getDestinyAddress() {
        return destinyAddress;
    }

    public void setDestinyAddress(String destinyAddress) {
        this.destinyAddress = destinyAddress;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Request [cart=" + cart + ", destinyAddress=" + destinyAddress + ", id=" + id + ", riderId=" + riderId
                + "]";
    }

    

}
