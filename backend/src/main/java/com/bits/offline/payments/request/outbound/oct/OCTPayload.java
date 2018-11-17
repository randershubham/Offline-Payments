package com.bits.offline.payments.request.outbound.oct;

import com.bits.offline.payments.request.outbound.common.CardAcceptor;
import com.bits.offline.payments.utils.Singletons;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OCTPayload {

    //hardcoded
    private String acquirerCountryCode;
    private String acquiringBin;
    private String businessApplicationId;
    private String localTransactionDateTime;
    private String retrievalReferenceNumber;
    private String systemsTraceAuditNumber;
    private String merchantCategoryCode;
    private String sourceOfFundsCode;
    private String transactionCurrencyCode;
    private String transactionIdentifier;
    private CardAcceptor cardAcceptor;
    private PointOfServiceData pointOfServiceData;

    // Not hardcoded
    private String recipientName;
    private String recipientPrimaryAccountNumber;
    private String senderAccountNumber;
    private String senderAddress;
    private String senderCity;
    private String senderCountryCode;
    private String senderName;
    private String senderReference;
    private String senderStateCode;
    private String amount;

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

    public String getBusinessApplicationId() {
        return businessApplicationId;
    }

    public void setBusinessApplicationId(String businessApplicationId) {
        this.businessApplicationId = businessApplicationId;
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

    public String getSystemsTraceAuditNumber() {
        return systemsTraceAuditNumber;
    }

    public void setSystemsTraceAuditNumber(String systemsTraceAuditNumber) {
        this.systemsTraceAuditNumber = systemsTraceAuditNumber;
    }

    public String getMerchantCategoryCode() {
        return merchantCategoryCode;
    }

    public void setMerchantCategoryCode(String merchantCategoryCode) {
        this.merchantCategoryCode = merchantCategoryCode;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPrimaryAccountNumber() {
        return recipientPrimaryAccountNumber;
    }

    public void setRecipientPrimaryAccountNumber(String recipientPrimaryAccountNumber) {
        this.recipientPrimaryAccountNumber = recipientPrimaryAccountNumber;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getSenderCountryCode() {
        return senderCountryCode;
    }

    public void setSenderCountryCode(String senderCountryCode) {
        this.senderCountryCode = senderCountryCode;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderReference() {
        return senderReference;
    }

    public void setSenderReference(String senderReference) {
        this.senderReference = senderReference;
    }

    public String getSenderStateCode() {
        return senderStateCode;
    }

    public void setSenderStateCode(String senderStateCode) {
        this.senderStateCode = senderStateCode;
    }

    public String getSourceOfFundsCode() {
        return sourceOfFundsCode;
    }

    public void setSourceOfFundsCode(String sourceOfFundsCode) {
        this.sourceOfFundsCode = sourceOfFundsCode;
    }

    public String getTransactionCurrencyCode() {
        return transactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    public CardAcceptor getCardAcceptor() {
        return cardAcceptor;
    }

    public void setCardAcceptor(CardAcceptor cardAcceptor) {
        this.cardAcceptor = cardAcceptor;
    }

    public PointOfServiceData getPointOfServiceData() {
        return pointOfServiceData;
    }

    public void setPointOfServiceData(PointOfServiceData pointOfServiceData) {
        this.pointOfServiceData = pointOfServiceData;
    }

    public String toJson() throws JsonProcessingException {
        return Singletons.INSTANCE.getObjectMapper().writeValueAsString(this);
    }
}
