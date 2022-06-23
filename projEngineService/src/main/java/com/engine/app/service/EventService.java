package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engine.app.exception.ConflictException;
import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;
import com.engine.app.repository.EventRepository;
<<<<<<< HEAD
import com.engine.app.exception.ResourceNotFoundException;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;

import com.google.gson.JsonObject;
>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Delivery delivery, DeliveryStatus status) throws ConflictException {
<<<<<<< HEAD
        Event event = new Event(delivery, status);
        eventRepository.save(event);
        return event;
    }

    public Event getDeliveryEvent(Delivery delivery) throws ResourceNotFoundException {
        Event event = eventRepository.findTop1ByDeliveryOrderByTimestampDesc(delivery);
        if (event != null) {
=======
        if (eventRepository.findByDeliveryAndStatus(delivery,status) != null) {
            throw new ConflictException("Event already exists");
        } else {
            Event event = new Event(delivery, status);
            eventRepository.save(event);

            JsonObject payload = new JsonObject();
            payload.addProperty("id", delivery.getId().toString() );
            payload.addProperty("status", event.getStatus().toString());

            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            //headers.setBearerAuth(BusinessUser.businessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(payload.toString() ,headers);
    
            ResponseEntity<Object> response = template.exchange("http://specific-service:8000/requestEvents/addEvent", HttpMethod.POST, entity, Object.class);

>>>>>>> 320026fcf8651a1b872487133b560c32ab5d9790
            return event;
        } else {
            throw new ResourceNotFoundException("Event doesn't exist");
        }
    }
    
}
