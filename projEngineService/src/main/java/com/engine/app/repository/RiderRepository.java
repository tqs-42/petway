package com.engine.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.engine.app.model.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {

    Rider findByEmail(String email);

    List<Rider> findAll();
    
    @Query("SELECT r FROM Rider r WHERE r.isActive = true")
    List<Rider> findAllActiveRiders();

}