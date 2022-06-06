package com.specific.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "REQUEST_PRODUCTS")
public class RequestProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestProductsId", nullable = false)
    private long id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "cartId", nullable = false)
    private Cart cart;  

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    public RequestProducts(int amount, Cart cart, Product product) {
        this.amount = amount;
        this.cart = cart;
        this.product = product;
    }

    public RequestProducts(){
        
    }

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }  

    
}
