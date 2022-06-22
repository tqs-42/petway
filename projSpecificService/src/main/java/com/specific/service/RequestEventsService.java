package com.specific.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.model.Product;
import com.specific.model.RequestEvents;
import com.specific.repository.RequestEventsRepository;

@Service
public class RequestEventsService {

    @Autowired
    private RequestEventsRepository requestEventsRepository;

    public Set<RequestEvents> getAllRequestEvents(String email) {
        Set<RequestEvents> requestEvents = requestEventsRepository.findRequestEventsByEmail(email);
        return requestEvents;
    }

    public RequestEvents getRequestEvents(long id) {
        RequestEvents requestEvents = requestEventsRepository.findRequestEventsByRequestId(id);
        return requestEvents;
    }

    public List<Map<String, Object>> getProductsInfoByOrderId(long orderId) {
        return requestEventsRepository.getProductByOrderId(orderId);
    }

}
