
package com.example.fatih.wirelesscomchat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferInfo {

    @SerializedName("senderDetails")
    @Expose
    private SenderDetails senderDetails;
    @SerializedName("recipientDetails")
    @Expose
    private RecipientDetails recipientDetails;
    @SerializedName("transactionDetails")
    @Expose
    private TransactionDetails transactionDetails;

    public SenderDetails getSenderDetails() {
        return senderDetails;
    }

    public void setSenderDetails(SenderDetails senderDetails) {
        this.senderDetails = senderDetails;
    }

    public RecipientDetails getRecipientDetails() {
        return recipientDetails;
    }

    public void setRecipientDetails(RecipientDetails recipientDetails) {
        this.recipientDetails = recipientDetails;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Override
    public String toString() {
        return "TransferInfo{" +
                "senderDetails=" + senderDetails +
                ", recipientDetails=" + recipientDetails +
                ", transactionDetails=" + transactionDetails +
                '}';
    }
}
