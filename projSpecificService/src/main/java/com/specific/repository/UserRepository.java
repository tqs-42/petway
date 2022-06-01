package com.specific.repository;

import com.specific.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    User findByEmail(String email);
	void deleteByEmail(String email);
}