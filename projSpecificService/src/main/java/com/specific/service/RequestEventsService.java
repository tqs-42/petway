package com.specific.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.specific.model.RequestEvents;
import com.specific.repository.RequestEventsRepository;

@Service
public class RequestEventsService {

    @Autowired
    private RequestEventsRepository requestEventsRepository;

    public Set<RequestEvents> getAllRequestEvents(String email) {
        return requestEventsRepository.findRequestEventsByEmail(email);
    }

    public RequestEvents getRequestEvents(long id) {
        return requestEventsRepository.findRequestEventsByRequestId(id);
    }

    public List<Map<String, Object>> getProductsInfoByOrderId(long orderId) {
        return requestEventsRepository.getProductByOrderId(orderId);
    }

}
