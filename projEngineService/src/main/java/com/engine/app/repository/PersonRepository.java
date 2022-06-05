package com.engine.app.repository;

import com.engine.app.model.Person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Optional<Person> findByEmail(String email);

    List<Person> findAll();

    // List<Person> findAllActiveRiders();

}