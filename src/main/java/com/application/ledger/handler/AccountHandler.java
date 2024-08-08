package com.application.ledger.handler;

import org.springframework.web.bind.annotation.RestController;

import com.application.ledger.service.AccountsManager;
import com.application.ledger.dto.CreateAccountRequest;
import com.application.ledger.dto.CreateAccountResponse;
import com.application.ledger.dto.GetAccountResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/* Handles all the account related operations */
@RestController
public class AccountHandler {
    
    @Autowired
    AccountsManager service;

    /* Create a new user account */
    @PostMapping(value = "/v1/account")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody CreateAccountRequest request) {
        return service.createAccount(request);
    }

    /* Get the info of account with id */
   @GetMapping(value = "/v1/account/{id}")
    public ResponseEntity<GetAccountResponse> getAccount(@PathVariable("id") String accountID) {
        return service.getAccount(accountID);
    }
    
}
