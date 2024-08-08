package com.application.ledger.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTransaction implements Serializable{
    private static final long serialVersionUID = 1L;

    @JsonProperty("account_id")
    private String accountID;

    @JsonProperty("operation_type_id")
    private int operationTypeId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public int getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(int operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
}
