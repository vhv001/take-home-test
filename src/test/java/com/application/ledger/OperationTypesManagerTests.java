package com.application.ledger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import com.application.ledger.dto.CreateOperationTypeRequest;
import com.application.ledger.entity.OperationType;
import com.application.ledger.repo.AccountRepository;
import com.application.ledger.repo.OperationTypeRepository;
import com.application.ledger.service.AccountsManager;
import com.application.ledger.service.OperationTypesManager;

@SpringBootTest
@SuppressWarnings("all")
public class OperationTypesManagerTests {
    
    @Autowired
    private OperationTypesManager manager;
    @MockBean
    private OperationTypeRepository repository;

    @Test
    public void createOperationType_Success(){
        CreateOperationTypeRequest request = new CreateOperationTypeRequest();
        request.setDebitOrCredit("CREDIT");
        request.setOperationType(2);
        request.setDescription("Rewards payout");

        when(repository.save(Mockito.any())).thenReturn(null);

        ResponseEntity<String> response = manager.createOperationType(request);

        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(201));
    }

    @Test
    public void createOperationType_Failure_400(){
        CreateOperationTypeRequest request = new CreateOperationTypeRequest();
        request.setOperationType(2);
        request.setDescription("Rewards payout");
        ResponseEntity<String> response = manager.createOperationType(request);

        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(400));
    }

    @Test
    public void createOperationType_Failure_500(){
        CreateOperationTypeRequest request = new CreateOperationTypeRequest();
        request.setOperationType(2);
        request.setDebitOrCredit("CREDIT");
        request.setDescription("Rewards payout");

        when(repository.save(Mockito.any())).thenThrow(RuntimeException.class);

        ResponseEntity<String> response = manager.createOperationType(request);
        assertTrue(response != null);
        assertTrue(response.getStatusCode() == HttpStatusCode.valueOf(500));
    }

}
