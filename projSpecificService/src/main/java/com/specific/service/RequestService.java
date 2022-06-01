package com.specific.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.specific.model.Request;
import com.specific.repository.RequestRepository;

@Service
public class RequestService {
    @Autowired
    private RequestRepository repository;

    public Request saveRequest(Request request) {
        return repository.save(request);
    }

    public List<Request> saveRequests(List<Request> requests) {
        return repository.saveAll(requests);
    }

    public List<Request> getRequests() {
        return repository.findAll();
    }

    public Request getRequestById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteRequest(long id) {
        repository.deleteById(id);
        return "request removed !! " + id;
    }

    public Request updateRequest(Request request) {
        Request existingRequest = repository.findById(request.getId()).orElse(null);
        existingRequest.setRiderId(request.getRiderId());
        existingRequest.setDestinyAddress(request.getDestinyAddress());
        existingRequest.setCart(request.getCart());
        existingRequest.setRequestEvents(request.getRequestEvents());
        return repository.save(existingRequest);
    }
}
