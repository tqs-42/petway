package com.engine.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.util.xml.StaxUtils;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.engine.app.exception.ConflictException;
import com.engine.app.exception.ResourceNotFoundException;
import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Event;
import com.engine.app.model.Review;
import com.engine.app.model.Rider;
import com.engine.app.model.Store;
import com.engine.app.repository.DeliveryRepository;
import com.engine.app.repository.EventRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private EventRepository eventRepository;
    
    public Delivery getDelivery(Long id) throws ResourceNotFoundException {
        Optional<Delivery> optional = deliveryRepository.findById(id);
        if (optional.isPresent()) return optional.get();
        else throw new ResourceNotFoundException("Delivery doesn't exist");
    }

    public Delivery createDelivery(Store store) throws ConflictException {
        Delivery delivery = new Delivery(null,null,store);
        deliveryRepository.save(delivery);
        return delivery;
    }

    public Delivery setDeliveryRider(Delivery delivery, Rider rider) {
        delivery.setRider(rider);
        return deliveryRepository.save(delivery);
    }

    public Delivery setDeliveryReview(Delivery delivery, Review review) {
        delivery.setReview(review);
        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<Delivery> getAllDeliveriesByStatus(DeliveryStatus status) {
        List<Delivery> deliveries = new ArrayList<>();
        System.out.println("get all deliveries");
        List<Event> events = eventRepository.findByStatus(status);
        System.out.println(events);
        for (Event e : events) {
            if (eventRepository.findTop1ByDeliveryOrderByTimestampDesc(e.getDelivery()) == e)
                deliveries.add(e.getDelivery());
        }
        System.out.println(deliveries);
        return deliveries;
    }

    public List<Delivery> getRiderDeliveries(Rider rider) {
        return deliveryRepository.findByRider(rider);
    }

    public List<Delivery> getStoreDeliveries(Store store) {
        return deliveryRepository.findByStore(store);
    }
    
}