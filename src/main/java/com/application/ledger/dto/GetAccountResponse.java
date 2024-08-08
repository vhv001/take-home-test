package com.application.ledger.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GetAccountResponse implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonProperty("account_id")
    private String accountID;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("document_id")
    private String documentID;

    @JsonProperty("account_opening_timestamp")
    private Timestamp accountOpeningTimestamp;

    @JsonProperty("status")
    private String status;

    public String getAccountID() {
        return accountID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getProductName() {
        return productName;
    }

    public Timestamp getAccountOpeningTimestamp() {
        return accountOpeningTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAccountOpeningTimestamp(Timestamp accountOpeningTimestamp) {
        this.accountOpeningTimestamp = accountOpeningTimestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}

