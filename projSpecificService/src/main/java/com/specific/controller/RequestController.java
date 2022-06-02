package com.specific.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.specific.model.Request;
import com.specific.service.RequestService;

@RestController
@RequestMapping("/api/specific")
public class RequestController {
    @Autowired
    private RequestService service;

    @PostMapping("/addRequest")
    public Request addRequest(@RequestBody Request request) {
        return service.saveRequest(request);
    }

    @PostMapping("/addRequests")
    public List<Request> addRequests(@RequestBody List<Request> requests) {
        return service.saveRequests(requests);
    }

    @GetMapping("/requests")
    public List<Request> findAllRequests() {
        return service.getRequests();
    }

    @GetMapping("/requestById/{id}")
    public Request findRequestById(@PathVariable int id) {
        return service.getRequestById(id);
    }

    @PutMapping("/updateRequest")
    public Request updateRequest(@RequestBody Request request) {
        return service.updateRequest(request);
    }

    @DeleteMapping("/deleteRequest/{id}")
    public String deleteRequest(@PathVariable int id) {
        return service.deleteRequest(id);
    }
}
