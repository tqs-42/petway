package com.specific.repository;

import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.specific.model.RequestEvents;

@Repository
public interface RequestEventsRepository extends JpaRepository<RequestEvents, Long> {

    @Query(value = "SELECT * FROM request_events as r JOIN request as req ON r.request_id = req.request_id JOIN cart as c ON req.cart_id = c.cart_id WHERE c.email = :email", nativeQuery = true)
    Set<RequestEvents> findRequestEventsByEmail(@Param("email") String email);

    @Query(value = "SELECT p.product_id, p.name, p.price, rp.amount FROM request_events as r JOIN request as req ON r.request_id = req.request_id JOIN cart as c ON req.cart_id = c.cart_id JOIN request_products as rp ON rp.cart_id = c.cart_id JOIN product as p ON p.product_id = rp.product_id WHERE r.request_id = :order_id", nativeQuery = true)
    Map<String, Object> getProductByOrderId(@Param("order_id") long order_id);

    RequestEvents findRequestEventsByRequestId(long requestId);

}