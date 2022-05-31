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
@Table(name = "rides")
public class Ride {

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

    @OneToOne(mappedBy = "ride")
    @Column(name = "review", nullable = true)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

}
