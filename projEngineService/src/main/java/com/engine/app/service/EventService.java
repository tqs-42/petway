package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engine.app.exception.ConflictException;
import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;
import com.engine.app.repository.EventRepository;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Delivery delivery, DeliveryStatus status) throws ConflictException {
        if (eventRepository.findByDeliveryAndStatus(delivery,status) != null) {
            throw new ConflictException("Event already exists");
        } else {
            Event event = new Event(delivery, status);
            eventRepository.save(event);
            return event;
        }
    }
    
}
