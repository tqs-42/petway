package com.engine.app.repository;

import com.engine.app.model.Ride;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride, Long> {

}