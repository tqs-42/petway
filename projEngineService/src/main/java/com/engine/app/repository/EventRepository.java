package com.engine.app.repository;

import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(DeliveryStatus status);

    Event findTop1ByDeliveryOrderByTimestampDesc(Delivery delivery);

}