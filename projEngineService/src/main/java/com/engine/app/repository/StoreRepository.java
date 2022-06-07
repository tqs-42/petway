package com.engine.app.repository;

import com.engine.app.model.Store;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>{

    Store findByName(String name);

    List<Store> findAll();
    
}
