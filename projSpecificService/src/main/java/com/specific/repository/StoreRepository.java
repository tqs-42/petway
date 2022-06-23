package com.specific.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.specific.model.Manager;
import com.specific.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findById(long id);

    Store findByName(String name);

    Store findByManager(Manager manager);
}
