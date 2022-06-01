package com.specific.repository;

import com.specific.model.RequestProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestProductsRepository extends JpaRepository<RequestProducts,Long> {
    
}
