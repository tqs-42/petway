package com.engine.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.engine.app.exception.ConflictException;
import com.engine.app.model.Person;
import com.engine.app.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Person registerPerson(String email,String password,String address, String fullname) throws ConflictException {
        if (personRepository.findByEmail(email).isPresent()) {
            System.out.println("email exists");
            throw new ConflictException("Person " + email + " already registered");
        } else {
            Person person = new Person(email, address, fullname);
            person.setPassword(passwordEncoder.encode(password));
            personRepository.save(person);
        }
        return null;
    }
}
    