package com.application.ledger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.application.ledger.dto.CreateTransaction;
import com.application.ledger.dto.CreateTransactionResponse;
import com.application.ledger.entity.OperationType;
import com.application.ledger.entity.Transaction;
import com.application.ledger.repo.AccountRepository;
import com.application.ledger.repo.OperationTypeRepository;
import com.application.ledger.repo.TransactionsRepository;
import com.application.ledger.service.AccountsManager;
import com.application.ledger.service.OperationTypesManager;
import com.application.ledger.service.TransactionsManager;

@SpringBootTest
@SuppressWarnings("all")
public class TransactionManagerTests {
    
    @Autowired
    private TransactionsManager transactionsManager;
    @MockBean
    private TransactionsRepository transactionsRepository;
    @MockBean
    private OperationTypesManager operationTypesManager;
    @MockBean
    private AccountsManager accountsManager;

    /* Test for successful payment */
    @Test
    public void createTransaction_Success(){
        CreateTransaction req = new CreateTransaction();
        req.setAccountID("acc1");
        req.setAmount("100");
        req.setCurrency("INR");
        req.setOperationTypeId(1);

        OperationType operationType = new OperationType();
        operationType.setOperationID(1);
        operationType.setOperationDescription("card auth");
        operationType.setDebitOrCredit("DEBIT");

        when(operationTypesManager.getOperationType(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(operationType));
        when(accountsManager.getAccount(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(null));
        when(transactionsRepository.save(Mockito.any())).thenReturn(null);
        
        ResponseEntity<CreateTransactionResponse> response = transactionsManager.createTransaction(req);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(201));
        assertTrue(response.getBody().getTransactionID() != null);
    }   

    /* Test when request has invalid fields */
    @Test
    public void createTransaction_Failure_400(){
        CreateTransaction req = new CreateTransaction();
        req.setAmount("100");
        req.setCurrency("INR");
        req.setOperationTypeId(1);
        ResponseEntity<CreateTransactionResponse> response = transactionsManager.createTransaction(req);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(400));
    }   

    /* Test when unknown account tries to trigger payment */
    @Test
    public void createTransaction_Failure_401(){
        CreateTransaction req = new CreateTransaction();
        req.setAccountID("acc1");
        req.setAmount("100");
        req.setCurrency("INR");
        req.setOperationTypeId(1);

        OperationType operationType = new OperationType();
        operationType.setOperationID(1);
        operationType.setOperationDescription("card auth");
        operationType.setDebitOrCredit("DEBIT");

        when(operationTypesManager.getOperationType(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(operationType));
        when(accountsManager.getAccount(Mockito.anyString())).thenReturn(ResponseEntity.status(204).body(null));
        
        ResponseEntity<CreateTransactionResponse> response = transactionsManager.createTransaction(req);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(401));
    }   

    /* Test for operation type provided by user is not supported by our system */
    @Test
    public void createTransaction_Failure_501(){
        CreateTransaction req = new CreateTransaction();
        req.setAccountID("acc1");
        req.setAmount("100");
        req.setCurrency("INR");
        req.setOperationTypeId(1);

        when(operationTypesManager.getOperationType(Mockito.anyString())).thenReturn(ResponseEntity.status(204).body(null));
        when(accountsManager.getAccount(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(null));
        when(transactionsRepository.save(Mockito.any())).thenReturn(null);
        
        ResponseEntity<CreateTransactionResponse> response = transactionsManager.createTransaction(req);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(501));
    }   

    /* Test when unknown error happens in business logic */
    @Test
    public void createTransaction_Success_500(){
        CreateTransaction req = new CreateTransaction();
        req.setAccountID("acc1");
        req.setAmount("100");
        req.setCurrency("INR");
        req.setOperationTypeId(1);

        OperationType operationType = new OperationType();
        operationType.setOperationID(1);
        operationType.setOperationDescription("card auth");
        operationType.setDebitOrCredit("DEBIT");

        when(operationTypesManager.getOperationType(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(operationType));
        when(accountsManager.getAccount(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(null));
        when(transactionsRepository.save(Mockito.any())).thenThrow(RuntimeException.class);
        
        ResponseEntity<CreateTransactionResponse> response = transactionsManager.createTransaction(req);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(500));
    }   
}
