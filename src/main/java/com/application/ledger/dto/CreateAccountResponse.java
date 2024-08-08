package com.application.ledger.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccountResponse implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @JsonProperty("account_id")
    private String accountID;

    @JsonProperty("account_opening_timestamp")
    private Timestamp accountOpeningTimestamp;

    @JsonProperty("account_status")
    private String accountStatus; 

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public Timestamp getAccountOpeningTimestamp() {
        return accountOpeningTimestamp;
    }

    public void setAccountOpeningTimestamp(Timestamp accountOpeningTimestamp) {
        this.accountOpeningTimestamp = accountOpeningTimestamp;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    
}
