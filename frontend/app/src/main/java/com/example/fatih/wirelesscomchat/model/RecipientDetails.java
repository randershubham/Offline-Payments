
package com.example.fatih.wirelesscomchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipientDetails {

    @SerializedName("recipientName")
    @Expose
    private String recipientName;
    @SerializedName("recipientPrimaryAccountNumber")
    @Expose
    private String recipientPrimaryAccountNumber;
    @SerializedName("recipientMobileNumber")
    @Expose
    private String recipientMobileNumber;
    @SerializedName("recipientEmailAddress")
    @Expose
    private String recipientEmailAddress;

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

    public String getRecipientMobileNumber() {
        return recipientMobileNumber;
    }

    public void setRecipientMobileNumber(String recipientMobileNumber) {
        this.recipientMobileNumber = recipientMobileNumber;
    }

    public String getRecipientEmailAddress() {
        return recipientEmailAddress;
    }

    public void setRecipientEmailAddress(String recipientEmailAddress) {
        this.recipientEmailAddress = recipientEmailAddress;
    }

    @Override
    public String toString() {
        return "RecipientDetails{" +
                "recipientName='" + recipientName + '\'' +
                ", recipientPrimaryAccountNumber='" + recipientPrimaryAccountNumber + '\'' +
                ", recipientMobileNumber='" + recipientMobileNumber + '\'' +
                ", recipientEmailAddress='" + recipientEmailAddress + '\'' +
                '}';
    }
}
