package com.application.ledger.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.ledger.dto.CreateTransaction;
import com.application.ledger.dto.CreateTransactionResponse;
import com.application.ledger.service.TransactionsManager;

/* Manages all the transactions related operations */
@RestController
public class TransactionsHandler {
    
    @Autowired
    private TransactionsManager service;

    /* Create a transaction for a given type and amount */
    @PostMapping(value = "/v1/transaction")
    public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestBody CreateTransaction request) {
        return service.createTransaction(request);
    }
}
