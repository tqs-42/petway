package com.engine.app.repository;

import com.engine.app.model.Rider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {

    Rider findByEmail(String email);

    List<Rider> findAll();
    
    @Query("SELECT r FROM Rider r WHERE r.isActive = true")
    List<Rider> findAllActiveRiders();

}