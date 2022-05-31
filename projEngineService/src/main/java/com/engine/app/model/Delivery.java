package com.engine.app.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "requestedAt", nullable = false)
    private Timestamp requestedAt;

    @Column(name = "deliveredAt", nullable = true)
    private Timestamp deliveredAt;

    @Column(name = "requestId", nullable = false)
    private Long requestId;

    @OneToOne(mappedBy = "delivery")
    @Column(name = "review", nullable = true)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    public Delivery(Timestamp requestedAt, Timestamp deliveredAt, Long requestId, Review review, Rider rider) {
        this.requestedAt = requestedAt;
        this.deliveredAt = deliveredAt;
        this.requestId = requestId;
        this.review = review;
        this.rider = rider;
    }

    public Delivery() {
    }

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    

}
