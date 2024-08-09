package com.application.ledger.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="operation_types")
public class OperationType {
    
    @Id
    @Column(name = "operation_id")
    @JsonProperty("operation_id")
    private Integer operationID;

    @Column(name = "description")
    @JsonProperty("description")
    private String operationDescription;

    @Column(name = "debit_or_credit")
    @JsonProperty("debit_or_credit")
    private String debitOrCredit;

    public Integer getOperationID() {
        return operationID;
    }

    public void setOperationID(Integer operationID) {
        this.operationID = operationID;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
    
}
