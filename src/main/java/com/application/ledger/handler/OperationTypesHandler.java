package com.application.ledger.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.application.ledger.dto.CreateOperationTypeRequest;
import com.application.ledger.entity.OperationType;
import com.application.ledger.service.OperationTypesManager;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/* Manages the different transaction/operation types that our system can support */
@RestController
public class OperationTypesHandler {
    
    @Autowired
    private OperationTypesManager operationTypesManager;

    /* Create a new operation */
    @PostMapping("/v1/operationtype")
    public ResponseEntity<String> createOperationType(@RequestBody CreateOperationTypeRequest request) {
        return operationTypesManager.createOperationType(request);
    }

    /* Get opeartion type details by id*/
    @GetMapping("/v1/operationtype/{id}")
    public ResponseEntity<OperationType> getOperationType(@RequestParam("id") String operationType) {
        return operationTypesManager.getOperationType(operationType);
    }
    
}
