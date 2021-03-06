package com.sample.ecommerce.data.mysql.repository;

import java.util.List;

import com.sample.ecommerce.data.mysql.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByBuyerId(Integer buyerId);
}