
package com.example.fatih.wirelesscomchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SenderDetails {

    @SerializedName("senderAccountNumber")
    @Expose
    private String senderAccountNumber;
    @SerializedName("senderAddress")
    @Expose
    private String senderAddress;
    @SerializedName("senderCity")
    @Expose
    private String senderCity;
    @SerializedName("senderCountryCode")
    @Expose
    private String senderCountryCode;
    @SerializedName("senderName")
    @Expose
    private String senderName;
    @SerializedName("senderReference")
    @Expose
    private String senderReference;
    @SerializedName("senderStateCode")
    @Expose
    private String senderStateCode;
    @SerializedName("senderCardExpiryDate")
    @Expose
    private String senderCardExpiryDate;
    @SerializedName("cavv")
    @Expose
    private String cvv;
    @SerializedName("senderMobileNumber")
    @Expose
    private String senderMobileNumber;
    @SerializedName("senderEmailAddress")
    @Expose
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
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

    @Override
    public String toString() {
        return "SenderDetails{" +
                "senderAccountNumber='" + senderAccountNumber + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", senderCity='" + senderCity + '\'' +
                ", senderCountryCode='" + senderCountryCode + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderReference='" + senderReference + '\'' +
                ", senderStateCode='" + senderStateCode + '\'' +
                ", senderCardExpiryDate='" + senderCardExpiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", senderMobileNumber='" + senderMobileNumber + '\'' +
                ", senderEmailAddress='" + senderEmailAddress + '\'' +
                '}';
    }
}
