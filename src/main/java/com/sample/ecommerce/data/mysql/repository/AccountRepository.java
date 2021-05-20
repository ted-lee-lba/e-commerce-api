package com.sample.ecommerce.data.mysql.repository;

import java.util.List;
import java.util.Optional;

import com.sample.ecommerce.data.mysql.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByUserName(String userName);
    List<Account> findByAccountType(String accountType);
}