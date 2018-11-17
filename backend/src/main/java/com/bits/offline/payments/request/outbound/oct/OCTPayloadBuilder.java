package com.bits.offline.payments.request.outbound.oct;

import com.bits.offline.payments.request.inbound.PaymentRequest;
import com.bits.offline.payments.request.inbound.RecipientDetails;
import com.bits.offline.payments.request.inbound.SenderDetails;
import com.bits.offline.payments.request.outbound.common.CardAcceptor;
import com.bits.offline.payments.utils.Singletons;

import java.util.Date;

public abstract class OCTPayloadBuilder {

    private static final String ACQUIRING_BIN = "408999";
    private static final String ACQUIRER_COUNTRY_CODE = "840";
    private static final String AMOUNT = "124.02";
    private static final String BUSINESS_APPLICATION_ID = "AA";
    private static final String RETRIEVAL_REFERENCE_NUMBER = "412770451018";
    private static final String SYSTEMS_TRACE_AUDIT_NUMBER = "451001";
    private static final String MERCHANT_CATEGORY_CODE = "6012";

    private static final String SOURCE_OF_FUNDS_CODE = "05";
    private static final String TRANSACTION_CURRENCY_CODE = "USD";
    private static final String TRANSACTION_IDENTIFIER = "381228649430015";

    private static final String RECIPIENT_NAME = "rohan";
    private static final String RECIPIENT_PRIMARY_ACCOUNT_NUMBER = "4957030420210496";
    private static final String SENDER_ACCOUNT_NUMBER = "4653459515756154";
    private static final String SENDER_ADDRESS = "901 Metro Center Blvd";
    private static final String SENDER_CITY = "Foster City";
    private static final String SENDER_COUNTRY_CODE = "124";
    private static final String SENDER_NAME= "Mohammed Qasim";
    private static final String SENDER_REFERENCE = "";
    private static final String SENDER_STATE_CODE = "CA";

    private OCTPayloadBuilder() {
    }

    public static OCTPayload createOCTPayload(String request) {

        OCTPayload octPayload = new OCTPayload();

        octPayload.setAcquirerCountryCode(ACQUIRER_COUNTRY_CODE);
        octPayload.setAcquiringBin(ACQUIRING_BIN);
        octPayload.setAmount(AMOUNT);
        octPayload.setBusinessApplicationId(BUSINESS_APPLICATION_ID);
        octPayload.setRetrievalReferenceNumber(RETRIEVAL_REFERENCE_NUMBER);
        octPayload.setSystemsTraceAuditNumber(SYSTEMS_TRACE_AUDIT_NUMBER);
        octPayload.setMerchantCategoryCode(MERCHANT_CATEGORY_CODE);
        octPayload.setRecipientName(RECIPIENT_NAME);
        octPayload.setRecipientPrimaryAccountNumber(RECIPIENT_PRIMARY_ACCOUNT_NUMBER);
        octPayload.setSenderAccountNumber(SENDER_ACCOUNT_NUMBER);
        octPayload.setSenderAddress(SENDER_ADDRESS);
        octPayload.setSenderCity(SENDER_CITY);
        octPayload.setSenderCountryCode(SENDER_COUNTRY_CODE);
        octPayload.setSenderName(SENDER_NAME);
        octPayload.setSenderReference(SENDER_REFERENCE);
        octPayload.setSenderStateCode(SENDER_STATE_CODE);
        octPayload.setSourceOfFundsCode(SOURCE_OF_FUNDS_CODE);
        octPayload.setTransactionCurrencyCode(TRANSACTION_CURRENCY_CODE);
        octPayload.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
        octPayload.setLocalTransactionDateTime(getCurrentTransactionTime());
        octPayload.setCardAcceptor(CardAcceptor.createCardAcceptor());
        octPayload.setPointOfServiceData(PointOfServiceData.createPointOfServiceData());

        return octPayload;
    }

    public static OCTPayload createOCTPayload(PaymentRequest paymentRequest) {

        RecipientDetails recipientDetails = paymentRequest.getRecipientDetails();
        SenderDetails senderDetails = paymentRequest.getSenderDetails();

        OCTPayload octPayload = new OCTPayload();

        octPayload.setAcquirerCountryCode(ACQUIRER_COUNTRY_CODE);
        octPayload.setAcquiringBin(ACQUIRING_BIN);
        octPayload.setBusinessApplicationId(BUSINESS_APPLICATION_ID);
        octPayload.setRetrievalReferenceNumber(RETRIEVAL_REFERENCE_NUMBER);
        octPayload.setSystemsTraceAuditNumber(SYSTEMS_TRACE_AUDIT_NUMBER);
        octPayload.setMerchantCategoryCode(MERCHANT_CATEGORY_CODE);
        octPayload.setSourceOfFundsCode(SOURCE_OF_FUNDS_CODE);
        octPayload.setTransactionCurrencyCode(TRANSACTION_CURRENCY_CODE);
        octPayload.setTransactionIdentifier(TRANSACTION_IDENTIFIER);
        octPayload.setLocalTransactionDateTime(getCurrentTransactionTime());
        octPayload.setCardAcceptor(CardAcceptor.createCardAcceptor());
        octPayload.setPointOfServiceData(PointOfServiceData.createPointOfServiceData());

        octPayload.setRecipientName(recipientDetails.getRecipientName());
        octPayload.setRecipientPrimaryAccountNumber(recipientDetails.getRecipientPrimaryAccountNumber());

        octPayload.setSenderAccountNumber(senderDetails.getSenderAccountNumber());
        octPayload.setSenderAddress(senderDetails.getSenderAddress());
        octPayload.setSenderCity(senderDetails.getSenderCity());
        octPayload.setSenderCountryCode(senderDetails.getSenderCountryCode());
        octPayload.setSenderName(senderDetails.getSenderName());
        octPayload.setSenderReference(senderDetails.getSenderReference());
        octPayload.setSenderStateCode(senderDetails.getSenderStateCode());

        octPayload.setAmount(paymentRequest.getTransactionDetails().getAmount());

        return octPayload;
    }

    private static String getCurrentTransactionTime() {
        return Singletons.INSTANCE.getDateFormat().format(new Date());
    }

}
