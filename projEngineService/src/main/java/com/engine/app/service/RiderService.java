package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


import com.engine.app.exception.ConflictException;

import java.util.List;

import com.engine.app.model.Rider;
import com.engine.app.repository.RiderRepository;

@Service
public class RiderService {

    @Autowired
    RiderRepository riderRepository;

    public Rider registerRider(String email,String password,String address, String fullname) throws ConflictException {
        if (riderRepository.findByEmail(email) != null) {
            throw new ConflictException("Rider " + email + " already registered");
        } else {
            Rider rider = new Rider(email, address, fullname);
            rider.setPassword(password);
            rider.setIsActive(true);
            riderRepository.save(rider);

        }
        return null;
    }
    
    public Rider getRiderByEmail(String email) {
        return riderRepository.findByEmail(email);
    }

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public List<Rider> getAllActiveRiders() {
        return riderRepository.findAllActiveRiders();
    }
    
    public Rider changeStatus(String email) throws BadCredentialsException {
        Rider rider = riderRepository.findByEmail(email);
        if (rider == null) {
            throw new BadCredentialsException("Rider with the email " + email + " does not exist"); 
        }
        rider.setIsActive(!rider.getIsActive());
        riderRepository.save(rider);
        return rider;
    }
    
}