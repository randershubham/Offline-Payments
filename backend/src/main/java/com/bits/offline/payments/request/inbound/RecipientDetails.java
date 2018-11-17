package com.bits.offline.payments.request.inbound;

public class RecipientDetails {

    private String recipientName;
    private String recipientPrimaryAccountNumber;
    private String recipientMobileNumber;
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
}
