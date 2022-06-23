package com.specific.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "request_events")
public class RequestEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_event_id", nullable = false)
    private long id;

    @Column(name = "request_id", nullable = false)
    private long requestId;

    @Column(name = "status", nullable = false)
    private RequestStatus status;

    @Column(name = "event_date", nullable = false)
    private Date eventDate;

    public RequestEvents(long requestId, RequestStatus status, Date eventDate) {
        this.requestId = requestId;
        this.status = status;
        this.eventDate = eventDate;
    }

    public RequestEvents() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
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
