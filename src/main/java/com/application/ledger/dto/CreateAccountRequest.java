package com.application.ledger.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CreateAccountRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("document_id")
    private String documentID;

    public String getDocumentID() {
        return documentID;
    }

    public String getProductName() {
        return productName;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }    
}

