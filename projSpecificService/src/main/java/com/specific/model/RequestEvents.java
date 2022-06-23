package com.specific.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "request_events")
public class RequestEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_event_id", nullable = false)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "requestId")
    private Request request;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RequestStatus status;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    public RequestEvents() {
    }

    public RequestEvents(Request request) {
        this.request = request;
        this.eventDate = new Date(System.currentTimeMillis());
        System.out.println("Vou agr estado");
        this.status = RequestStatus.PENDING;
        System.out.println("dps de estado");
    }

    public RequestEvents(Request request, RequestStatus status) {
        this.request = request;
        this.status = status;
        this.eventDate = new Date(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    
    
    
}
