package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engine.app.exception.ConflictException;
import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;
import com.engine.app.repository.EventRepository;
import com.engine.app.exception.ResourceNotFoundException;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Delivery delivery, DeliveryStatus status) throws ConflictException {
        Event event = new Event(delivery, status);
        eventRepository.save(event);
        return event;
    }

    public Event getDeliveryEvent(Delivery delivery) throws ResourceNotFoundException {
        Event event = eventRepository.findTop1ByDeliveryOrderByTimestampDesc(delivery);
        if (event != null) {
            return event;
        } else {
            throw new ResourceNotFoundException("Event doesn't exist");
        }
    }
    
}
