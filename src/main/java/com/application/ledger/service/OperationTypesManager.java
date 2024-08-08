package com.application.ledger.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.application.ledger.dto.CreateOperationTypeRequest;
import com.application.ledger.entity.OperationType;
import com.application.ledger.repo.OperationTypeRepository;

/*Service to manage the operation types */
@Service
public class OperationTypesManager {
    
    @Autowired
    private OperationTypeRepository repo;

    public ResponseEntity<String> createOperationType(final CreateOperationTypeRequest request) {
        if(!isValidOperationTypeCreationRequest(request)){
            return ResponseEntity.status(400).build();
        }
        try{
            OperationType operationType = new OperationType();
            operationType.setOperationDescription(request.getDescription());
            operationType.setOperationID(request.getOperationType());
            operationType.setDebitOrCredit(request.getDebitOrCredit());
            repo.save(operationType);
        }catch(Exception e){
            System.out.println("Error creating new operation type " + request.getOperationType() +" : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<OperationType> getOperationType(String operationType){
        Integer operationTypeInt = Integer.parseInt(operationType);
        // Operation type must be positive
        if(operationTypeInt <= 0){
            return ResponseEntity.status(400).build();
        }
        Optional<OperationType> response;
        try{
           response = repo.findById(operationTypeInt);
        }catch(Exception e){
            System.out.println("Error creating new operation type " + operationType +" : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        if(!response.isPresent()){
            return ResponseEntity.status(204).build();
        }
        
        return ResponseEntity.ok().body(response.get());
    }

    private boolean isValidOperationTypeCreationRequest(CreateOperationTypeRequest request){
        if(request == null || request.getOperationType() <= 0 || request.getDescription() == null || request.getDescription() == "" || request.getDebitOrCredit() == null || request.getDebitOrCredit() == ""){
            return false;
        }
        return true;
    }
    
}