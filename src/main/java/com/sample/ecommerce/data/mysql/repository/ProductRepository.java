package com.sample.ecommerce.data.mysql.repository;

import java.util.List;
import java.util.Optional;

import com.sample.ecommerce.data.mysql.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByProductCode(String productCode);
    Optional<Product> findByProductIdAndAccountId(Integer productId, Integer accountId);
    List<Product> findByAccountId(Integer accountId);
}