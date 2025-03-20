package com.mobile.api.repository;

import com.mobile.api.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameAndStatus(String name, Integer status);

    boolean existsByCategoryId(Long id);
}
