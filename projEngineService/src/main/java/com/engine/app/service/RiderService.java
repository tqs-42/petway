package com.engine.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.engine.app.model.Rider;
import com.engine.app.repository.RiderRepository;

@Service
public class RiderService {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

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
    
}
