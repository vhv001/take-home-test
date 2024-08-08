package com.application.ledger.repo;

import com.application.ledger.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* ORM layer to manage all data related to transactions related operations*/
@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer>{
    
}
