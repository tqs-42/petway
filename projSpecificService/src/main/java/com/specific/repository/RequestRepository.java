package com.specific.repository;

import com.specific.model.Cart;
import com.specific.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {
    Request findByCart(Cart cart);
}
