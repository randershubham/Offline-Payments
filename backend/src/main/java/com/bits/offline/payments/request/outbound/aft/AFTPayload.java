package com.bits.offline.payments.request.outbound.aft;

import com.bits.offline.payments.request.outbound.common.CardAcceptor;
import com.bits.offline.payments.utils.Singletons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AFTPayload {

    //hardcoded
    private String acquirerCountryCode;
    private String acquiringBin;
    private String businessApplicationId;
    private String foreignExchangeFeeTransaction;
    private String localTransactionDateTime;
    private String retrievalReferenceNumber;
    private String systemsTraceAuditNumber;
    private String surcharge;
    private String senderCurrencyCode;
    private CardAcceptor cardAcceptor;

    // Not hardcoded
    private String amount;
    private String cavv;
    private String senderCardExpiryDate;

    private String senderPrimaryAccountNumber;

    public String getAcquirerCountryCode() {
        return acquirerCountryCode;
    }

    public void setAcquirerCountryCode(String acquirerCountryCode) {
        this.acquirerCountryCode = acquirerCountryCode;
    }

    public String getAcquiringBin() {
        return acquiringBin;
    }

    public void setAcquiringBin(String acquiringBin) {
        this.acquiringBin = acquiringBin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    public String getBusinessApplicationId() {
        return businessApplicationId;
    }

    public void setBusinessApplicationId(String businessApplicationId) {
        this.businessApplicationId = businessApplicationId;
    }

    public String getForeignExchangeFeeTransaction() {
        return foreignExchangeFeeTransaction;
    }

    public void setForeignExchangeFeeTransaction(String foreignExchangeFeeTransaction) {
        this.foreignExchangeFeeTransaction = foreignExchangeFeeTransaction;
    }

    public String getLocalTransactionDateTime() {
        return localTransactionDateTime;
    }

    public void setLocalTransactionDateTime(String localTransactionDateTime) {
        this.localTransactionDateTime = localTransactionDateTime;
    }

    public String getRetrievalReferenceNumber() {
        return retrievalReferenceNumber;
    }

    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
        this.retrievalReferenceNumber = retrievalReferenceNumber;
    }

    public String getSenderCardExpiryDate() {
        return senderCardExpiryDate;
    }

    public void setSenderCardExpiryDate(String senderCardExpiryDate) {
        this.senderCardExpiryDate = senderCardExpiryDate;
    }

    public String getSenderCurrencyCode() {
        return senderCurrencyCode;
    }

    public void setSenderCurrencyCode(String senderCurrencyCode) {
        this.senderCurrencyCode = senderCurrencyCode;
    }

    public String getSenderPrimaryAccountNumber() {
        return senderPrimaryAccountNumber;
    }

    public void setSenderPrimaryAccountNumber(String senderPrimaryAccountNumber) {
        this.senderPrimaryAccountNumber = senderPrimaryAccountNumber;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    public String getSystemsTraceAuditNumber() {
        return systemsTraceAuditNumber;
    }

    public void setSystemsTraceAuditNumber(String systemsTraceAuditNumber) {
        this.systemsTraceAuditNumber = systemsTraceAuditNumber;
    }

    public CardAcceptor getCardAcceptor() {
        return cardAcceptor;
    }

    public void setCardAcceptor(CardAcceptor cardAcceptor) {
        this.cardAcceptor = cardAcceptor;
    }

    public String toJson() throws JsonProcessingException {
        return Singletons.INSTANCE.getObjectMapper().writeValueAsString(this);
    }
}

