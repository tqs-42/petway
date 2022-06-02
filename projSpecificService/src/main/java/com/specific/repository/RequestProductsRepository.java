package com.specific.repository;

import com.specific.model.RequestProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestProductsRepository extends JpaRepository<RequestProducts,Long> {
    
}
