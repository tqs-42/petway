package com.engine.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engine.app.model.Rider;
import com.engine.app.repository.RiderRepository;

@Service
public class RiderService {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Rider save(Rider rider) throws Exception {
        
        if (riderRepository.findByEmail(rider.getEmail()).isEmpty()) {

            rider.setPassword(passwordEncoder.encode(rider.getPassword()));
            riderRepository.saveAndFlush(rider);
            return rider;

        } else {
            
            throw new Exception();

        }

    }

    public Rider getRiderByEmail(String email) {
        return riderRepository.findByEmail(email).get();
    }

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    public List<Rider> getAllActiveRiders() {
        return riderRepository.findAllActiveRiders();
    }
    
    public void changeStatus(String email) {
        Rider rider = riderRepository.findByEmail(email).get();
        rider.setIsActive(!rider.getIsActive());
        riderRepository.save(rider);
    }
    
}
