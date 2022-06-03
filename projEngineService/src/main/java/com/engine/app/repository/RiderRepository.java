package com.engine.app.repository;

import com.engine.app.model.Rider;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, String> {

    Rider findByEmail(String email);


}