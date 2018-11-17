package com.bits.offline.payments.request.inbound;

public class SenderDetails {

    private String senderAccountNumber;
    private String senderAddress;
    private String senderCity;
    private String senderCountryCode;
    private String senderName;
    private String senderReference;
    private String senderStateCode;
    private String senderCardExpiryDate;
    private String cavv;
    private String senderMobileNumber;
    private String senderEmailAddress;

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

    public String getSenderCardExpiryDate() {
        return senderCardExpiryDate;
    }

    public void setSenderCardExpiryDate(String senderCardExpiryDate) {
        this.senderCardExpiryDate = senderCardExpiryDate;
    }

    public String getCavv() {
        return cavv;
    }

    public void setCavv(String cavv) {
        this.cavv = cavv;
    }

    public String getSenderMobileNumber() {
        return senderMobileNumber;
    }

    public void setSenderMobileNumber(String senderMobileNumber) {
        this.senderMobileNumber = senderMobileNumber;
    }

    public String getSenderEmailAddress() {
        return senderEmailAddress;
    }

    public void setSenderEmailAddress(String senderEmailAddress) {
        this.senderEmailAddress = senderEmailAddress;
    }
}
