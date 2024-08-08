package com.application.ledger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOperationTypeRequest {
    
    @JsonProperty("operation_type")
    private int operationType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("debit_or_credit")
    private String debitOrCredit;

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }

}
