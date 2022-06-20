package com.engine.app.repository;

import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE e.delivery = delivery AND e.status = status")
    Event findByDeliveryAndStatus(Delivery delivery, DeliveryStatus status);

}