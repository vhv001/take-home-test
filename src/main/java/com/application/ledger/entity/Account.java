package com.application.ledger.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="accounts")
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(name = "account_id")
    private String accountID;

    @Column(name = "document_id")
    private String documentID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "account_opening_time")
    private Timestamp accountOpeningTimestamp;

    @Column(name = "account_status")
    private String status;

    public int getId() {
        return id;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getProductName() {
        return productName;
    }
    
    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public Timestamp getAccountOpeningTimestamp() {
        return accountOpeningTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
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
