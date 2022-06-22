package com.engine.app.repository;

import com.engine.app.model.Delivery;
import com.engine.app.model.DeliveryStatus;
import com.engine.app.model.Rider;
import com.engine.app.model.Store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    List<Delivery> findByRider(Rider rider);

    List<Delivery> findByStore(Store store);

}