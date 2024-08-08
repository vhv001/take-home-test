package com.application.ledger.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.ledger.dto.AccountStatus;
import com.application.ledger.dto.CreateAccountRequest;
import com.application.ledger.dto.CreateAccountResponse;
import com.application.ledger.dto.GetAccountResponse;
import com.application.ledger.entity.Account;
import com.application.ledger.exceptions.AccountCreationException;
import com.application.ledger.repo.AccountRepository;

/* Service to handle the business logic related to user accounts */
@Service
public class AccountsManager {

    @Autowired
    private AccountRepository repo;
    private UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator();

    public ResponseEntity<CreateAccountResponse> createAccount(final CreateAccountRequest request) {
        // Validate the request, if any field is invalid, it is a bad request
        if (!isValidAccountCreationRequest(request)){
            return ResponseEntity.status(400).body(null);
        }
        final Account account = new Account();
        // Call unique Id generator method to create a new user account Id
        try {
            account.setAccountID(uniqueIdGenerator.generateUniqueId(15));
        } catch (AccountCreationException e) {
            e.printStackTrace();
            return ResponseEntity.status(503).body(null);
        }
        account.setDocumentID(request.getDocumentID());
        account.setProductName(request.getProductName());
        // By default, account status is active 
        account.setStatus(AccountStatus.ACTIVE.toString());
        account.setAccountOpeningTimestamp(new Timestamp(System.currentTimeMillis()));
        try{
            repo.save(account);
        }catch(Exception e){
            System.out.println("Error creating account for document Id" + account.getDocumentID() + " :" + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        CreateAccountResponse response = new CreateAccountResponse();
        // Return generated account Id to the user
        response.setAccountID(account.getAccountID());
        response.setAccountOpeningTimestamp(account.getAccountOpeningTimestamp());
        response.setAccountStatus(account.getStatus());
        return ResponseEntity.status(201).body(response);
    }

    @SuppressWarnings("all")
    public ResponseEntity<GetAccountResponse> getAccount(String accountID) {
        if (accountID == null || accountID == ""){
            return ResponseEntity.status(400).build();
        }
        Optional<Account> account = null;
        try{
            account = repo.findByAccountID(accountID);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error getting account info for account Id" + accountID + " :" + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        if (!account.isPresent()) {
            return ResponseEntity.status(204).build();
        }
        final GetAccountResponse response = new GetAccountResponse();
        response.setAccountID(account.get().getAccountID());
        response.setAccountOpeningTimestamp(account.get().getAccountOpeningTimestamp());
        response.setStatus(account.get().getStatus());
        response.setProductName(account.get().getProductName());
        response.setDocumentID(account.get().getDocumentID());
        return ResponseEntity.status(200).body(response);
    }

    /* Validates account creation request body */
    private boolean isValidAccountCreationRequest(final CreateAccountRequest request){
        if (request == null || request.getDocumentID() == "" || request.getDocumentID() == null
        || request.getProductName() == null || request.getProductName() == "") {
            return false;
        }
        return true;
    }

    /*Inner class to handle new account generation logic as part of account creation flow */
    private class UniqueIdGenerator {

        private static final int maxRetryCounter = 3;
        private static final ReentrantLock lock = new ReentrantLock();
        private static final List<String> list = Arrays.asList("A","B","0","C","E","E","F","1","G","H","3","I","2","J","K","4","L","M","9","N","O","5","P","Q","6","7","R","S","T","8","U","V","9","W","X","Y","Z");
        // For a given length, use random function to pick length number of random characters and build the unique id
        public String generateUniqueId(int length) throws AccountCreationException{
            try{
                // Lock to handle concurrency issues
                lock.lock();
                int counter = maxRetryCounter;
                while (counter-- > 0) {
                    Collections.shuffle(list);
                    StringBuilder sb = new StringBuilder();
                    Random r = new Random();
                    for(int i = 0 ; i <length ; i++){
                        int pos = r.nextInt(0, list.size());
                        sb.append(list.get(pos));
                    }
                    Optional<Account> response = repo.findByAccountID(sb.toString());
                    if (!response.isPresent()){
                        return sb.toString();    
                    }                    
                }
                throw new AccountCreationException("retries exhausted");
            }finally{
                lock.unlock();
            }
        }
    }

}
