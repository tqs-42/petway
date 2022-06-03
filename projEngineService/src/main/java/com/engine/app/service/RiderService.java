package com.engine.app.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.engine.app.model.Rider;
import com.engine.app.model.Person;
import com.engine.app.repository.PersonRepository;
import com.engine.app.repository.RiderRepository;
import com.engine.app.exception.ConflictException;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;



@Service
public class RiderService {

    @Autowired
    private PersonRepository personRepository;

    public Rider registerRider(String email, String password, String address, String fullname)  throws ConflictException {
        if (personRepository.findByEmail(email) != null) {
            throw new ConflictException("Rider " + email + " already registered");
        } else {
            Rider rider = new Rider(email, address, fullname, password);
            rider.setIsActive(true);
            personRepository.save(rider);
        }
        return null;
    }
    
    public Person getRiderByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public List<Person> getAllRiders() {
        return personRepository.findAll();
    }

    // public List<Person> getAllActiveRiders() {
    //     return personRepository.findAllActiveRiders();
    // }
    
    // public void changeStatus(String email) {
    //     Rider rider = personRepository.findByEmail(email);
    //     rider.setIsActive(!rider.getIsActive());
    //     personRepository.save(rider);
    // }

}
