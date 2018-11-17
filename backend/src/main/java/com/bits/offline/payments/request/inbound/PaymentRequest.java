package com.bits.offline.payments.request.inbound;

import com.bits.offline.payments.utils.Singletons;
import com.fasterxml.jackson.core.JsonProcessingException;

public class PaymentRequest {

    private SenderDetails senderDetails;
    private RecipientDetails recipientDetails;
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

    public String toJson() throws JsonProcessingException {
        return Singletons.INSTANCE.getObjectMapper().writeValueAsString(this);
    }
}
