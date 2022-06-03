package com.engine.app.repository;

import com.engine.app.model.Rider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {

    Optional<Rider> findByEmail(String email);

}