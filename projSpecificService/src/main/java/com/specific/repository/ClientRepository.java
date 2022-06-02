package com.specific.repository;

import com.specific.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
    Client findByEmail(String email);
	void deleteByEmail(String email);
}
