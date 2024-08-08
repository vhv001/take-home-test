package com.application.ledger.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ledger.entity.OperationType;

/* ORM layer to manage all data operations on different operation types our system can support */
@Repository 
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer>{
    
}
