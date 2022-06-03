package com.specific.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "REQUEST")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId", nullable = false)
    private long id;

    @Column(name = "riderId", nullable = false)
    private int riderId;

    @Column(name = "destinyAddress", nullable = false)
    private String destinyAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartId")
    private Cart cart;

    public Request(int riderId, String destinyAddress, Cart cart) {
        this.riderId = riderId;
        this.destinyAddress = destinyAddress;
        this.cart = cart;
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
    
}
