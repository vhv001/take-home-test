package com.application.ledger.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ledger.entity.Account;

/* ORM layer to manage all data operations on user accounts */
@Repository 
public interface AccountRepository extends JpaRepository<Account, Integer>{

    Optional<Account> findByAccountID(String accountID);
}