package com.application.ledger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.application.ledger.dto.CreateAccountRequest;
import com.application.ledger.dto.CreateAccountResponse;
import com.application.ledger.entity.Account;
import com.application.ledger.repo.AccountRepository;
import com.application.ledger.service.AccountsManager;

@SpringBootTest
@SuppressWarnings("all")
public class AccountManagerTests {
    
    @Autowired
    private AccountsManager manager;
    @MockBean
    private AccountRepository repository;

    /* Test account creation logic for success */
    @Test
    public void createAccountTest_Success(){
        when(repository.findByAccountID(Mockito.anyString())).thenReturn(Optional.empty());

        CreateAccountRequest request = new CreateAccountRequest();
        request.setDocumentID("doc-123");
        request.setProductName("cards");
        ResponseEntity<CreateAccountResponse> response = manager.createAccount(request);
        assertTrue(response != null);
        assertTrue(response.getBody() != null);
        assertTrue(response.getBody().getAccountID().length() == 15);
        assertTrue(response.getBody().getAccountStatus().equals("ACTIVE"));
    }

    /* Test account creation failure when account generation logic retries exhausts */
    @Test
    public void createAccountTest_Failure_503(){
        final Account account = new Account();
        when(repository.findByAccountID(Mockito.anyString())).thenReturn(Optional.of(account)).thenReturn(Optional.of(account)).thenReturn(Optional.of(account));

        CreateAccountRequest request = new CreateAccountRequest();
        request.setDocumentID("doc-123");
        request.setProductName("cards");
        ResponseEntity<CreateAccountResponse> response = manager.createAccount(request);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(503));
    }

    /* Test account creation failure when account generation logic retries exhausts */
    @Test
    public void createAccountTest_Failure_400(){
        CreateAccountRequest request = new CreateAccountRequest();
        request.setProductName("cards");
        ResponseEntity<CreateAccountResponse> response = manager.createAccount(request);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(400));
    }

    /* Test account creation failure during db operations */
    @Test
    public void createAccountTest_Failure_500(){
        when(repository.findByAccountID(Mockito.anyString())).thenReturn(Optional.empty());
        when(repository.save(Mockito.any(Account.class))).thenThrow(RuntimeException.class);
        CreateAccountRequest request = new CreateAccountRequest();
        request.setDocumentID("doc-123");
        request.setProductName("cards");
        ResponseEntity<CreateAccountResponse> response = manager.createAccount(request);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(500));
    }
}
