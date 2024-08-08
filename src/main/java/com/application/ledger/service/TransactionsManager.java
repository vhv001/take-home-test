package com.application.ledger.service;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.ledger.dto.CreateTransaction;
import com.application.ledger.dto.CreateTransactionResponse;
import com.application.ledger.dto.GetAccountResponse;
import com.application.ledger.dto.TransactionStatus;
import com.application.ledger.entity.OperationType;
import com.application.ledger.entity.Transaction;
import com.application.ledger.repo.TransactionsRepository;

/* Service to handle all the business logic to handle transactions */
@Service
public class TransactionsManager {
    
    @Autowired
    private TransactionsRepository repo;

    // Dependencies to call other services 
    @Autowired
    private AccountsManager userManager;
    @Autowired
    private OperationTypesManager operationTypesManager;

    
    @SuppressWarnings("null")
    public ResponseEntity<CreateTransactionResponse> createTransaction(final CreateTransaction request) {
        if (!isValidaCreateTransactionRequest(request)){
            return ResponseEntity.status(400).body(null);
        }
        // Check whether the user is a valid user
        final ResponseEntity<GetAccountResponse> accountResponse = userManager.getAccount(request.getAccountID());
        if(accountResponse.getStatusCode() != HttpStatusCode.valueOf(200)) {
            return ResponseEntity.status(401).build();
        }
        // Check whether our system supports the operation requested by user
        final ResponseEntity<OperationType> operationTypeResponse = operationTypesManager.getOperationType(String.valueOf(request.getOperationTypeId()));
        if (operationTypeResponse.getStatusCode() != HttpStatusCode.valueOf(200)){
            return ResponseEntity.status(501).build();
        }

        final Transaction transaction = new Transaction();
        transaction.setAccountID(request.getAccountID());
        transaction.setTransactionID(UUID.randomUUID().toString());
        transaction.setOperationType(request.getOperationTypeId());
        transaction.setStatus(TransactionStatus.SUCCESS.toString());
        transaction.setTransactionTime(new Timestamp(System.currentTimeMillis()));
        transaction.setCurrency(request.getCurrency());
        // Depending on type of transaction i.e DEBIT or CREDIT, amount will be negative if it is DEBIT, or positive. 
        if(operationTypeResponse.getBody().getDebitOrCredit().equals("DEBIT")){
            transaction.setAmount(String.valueOf(-Double.parseDouble(request.getAmount())));
        }else{
            transaction.setAmount(request.getAmount());
        }

        try{
            repo.save(transaction);
        }catch(Exception e){
            System.out.println("Error occurred for txn Id" + transaction.getTransactionID() + " :" + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        // Send the transaction id and status to user
        CreateTransactionResponse response = new CreateTransactionResponse();
        response.setStatus(transaction.getStatus());
        response.setTransactionID(transaction.getTransactionID());
        response.setTransactionTime(transaction.getTransactionTime());

        return ResponseEntity.status(201).body(response);
    }

    /* Validate the transaction request */
    private boolean isValidaCreateTransactionRequest(final CreateTransaction request){
        if (request == null || request.getAccountID() == null || request.getAccountID() == ""
            || request.getAmount() == null || request.getAmount() == "" || request.getOperationTypeId() == 0 || request.getCurrency() == null || request.getCurrency() == ""){
                return false;
            }
            return true;
    }

}
