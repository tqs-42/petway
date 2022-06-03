package com.engine.app.repository;

import com.engine.app.model.Person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {

    Person findByEmail(String email);

    List<Person> findAll();

    //List<Person> findAllActiveRiders();


}