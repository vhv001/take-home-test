package com.application.ledger.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransactionResponse {

    @JsonProperty("transaction_id")
    private String transactionID;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transaction_time")
    private Timestamp transactionTime;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    
}
