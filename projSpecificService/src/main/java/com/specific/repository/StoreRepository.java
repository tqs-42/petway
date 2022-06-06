package com.specific.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.specific.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    Store getStoreByManagers(String manager);

    List<Store> findAll();

    Store findById(long id);
    
}
