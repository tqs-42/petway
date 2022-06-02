package com.engine.app.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.engine.app.model.Rider;
import com.engine.app.repository.RiderRepository;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class RegisterRiderService {

    @Autowired
    private RiderRepository riderRepository;

    public Rider registerRider(String email, String password, String address, String fullname) {
        Rider rider = new Rider(email, address, fullname, password);

        riderRepository.save(rider);
        
    }

    

}
