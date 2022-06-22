package com.specific.repository;

import com.specific.model.Cart;
import com.specific.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByClient(Client client);
}
