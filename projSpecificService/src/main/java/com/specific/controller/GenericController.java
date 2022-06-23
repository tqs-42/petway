package com.specific.controller;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.RequestEvents;
import com.specific.service.GenericService;
import com.specific.service.RequestEventsService;

@RestController
@CrossOrigin(origins = {"http://localhost:4201", "http://localhost:6868", "http://0.0.0.0:6868"})
@RequestMapping("/generic")
public class GenericController {
    @Autowired
    private GenericService service;

    @PostMapping("/makeOrder/{email}")
    public ResponseEntity<String> makeOrder(@Valid @PathVariable String email) throws ResourceNotFoundException {
        System.out.println("!!!!!!!!!!Order!!!!!!!!!!!!!!!!!!");
        Map<String, String> data;
        try {
            data = service.makeOrder(email);
            System.out.println(data.get("store"));
            System.out.println(data.get("address"));

            JsonObject payload = new JsonObject();
            payload.addProperty("store", data.get("store"));
            payload.addProperty("address", data.get("address"));

            RestTemplate template = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            //headers.setBearerAuth(BusinessUser.businessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<String>(payload.toString() ,headers);
    
            ResponseEntity<Object> response = template.exchange("http://engine-service:8001/deliveries/delivery", HttpMethod.POST, entity, Object.class);
            System.out.println("RESPOTAAAAAAAAAAAAAAAAA");
            System.out.println(response);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
