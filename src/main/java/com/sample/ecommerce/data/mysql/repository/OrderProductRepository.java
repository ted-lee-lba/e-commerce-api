package com.sample.ecommerce.data.mysql.repository;

import com.sample.ecommerce.data.mysql.entity.OrderProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer>, JpaSpecificationExecutor<OrderProduct> {
}