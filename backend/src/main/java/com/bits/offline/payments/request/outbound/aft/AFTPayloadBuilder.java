package com.bits.offline.payments.request.outbound.aft;

import com.bits.offline.payments.request.inbound.PaymentRequest;
import com.bits.offline.payments.request.inbound.SenderDetails;
import com.bits.offline.payments.request.outbound.common.CardAcceptor;
import com.bits.offline.payments.utils.Singletons;

import java.util.Date;

public abstract class AFTPayloadBuilder {

    private static final String ACQUIRER_COUNTRY_CODE = "840";
    private static final String ACQUIRING_BIN = "408999";
    private static final String AMOUNT = "124.02";
    private static final String BUSINESS_APPLICATION_ID = "AA";
    private static final String FOREIGN_EXCHANGE_FEE_TRANSACTION = "11.99";
    private static final String RETRIEVAL_REFERENCE_NUMBER = "330000550000";

    private static final String SYSTEMS_TRACE_AUDIT_NUMBER = "451001";
    private static final String SURCHARGE = "11.99  ";

    private static final String CAVV = "0700100038238906000013405823891061668252";
    private static final String SENDER_CARD_EXPIRY_DATE = "2015-10";
    private static final String SENDER_CURRENCY_CODE = "USD";
    private static final String SENDER_PRIMARY_ACCOUNT_NUMBER = "4895142232120006";

    public static AFTPayload createAFTPayload(String payload) {

        AFTPayload aftPayload = new AFTPayload();

        aftPayload.setAcquirerCountryCode(ACQUIRER_COUNTRY_CODE);
        aftPayload.setAcquiringBin(ACQUIRING_BIN);
        aftPayload.setAmount(AMOUNT);
        aftPayload.setBusinessApplicationId(BUSINESS_APPLICATION_ID);
        aftPayload.setCardAcceptor(CardAcceptor.createCardAcceptor());
        aftPayload.setCavv(CAVV);
        aftPayload.setForeignExchangeFeeTransaction(FOREIGN_EXCHANGE_FEE_TRANSACTION);
        aftPayload.setLocalTransactionDateTime(getCurrentTransactionTime());
        aftPayload.setRetrievalReferenceNumber(RETRIEVAL_REFERENCE_NUMBER);
        aftPayload.setSenderCardExpiryDate(SENDER_CARD_EXPIRY_DATE);
        aftPayload.setSenderCurrencyCode(SENDER_CURRENCY_CODE);
        aftPayload.setSenderPrimaryAccountNumber(SENDER_PRIMARY_ACCOUNT_NUMBER);
        aftPayload.setSystemsTraceAuditNumber(SYSTEMS_TRACE_AUDIT_NUMBER);
        aftPayload.setSurcharge(SURCHARGE);

        return aftPayload;
    }

    public static AFTPayload createAFTPayload(PaymentRequest paymentRequest) {

        SenderDetails senderDetails = paymentRequest.getSenderDetails();

        AFTPayload aftPayload = new AFTPayload();

        aftPayload.setAcquirerCountryCode(ACQUIRER_COUNTRY_CODE);
        aftPayload.setAcquiringBin(ACQUIRING_BIN);
        aftPayload.setBusinessApplicationId(BUSINESS_APPLICATION_ID);
        aftPayload.setCardAcceptor(CardAcceptor.createCardAcceptor());
        aftPayload.setForeignExchangeFeeTransaction(FOREIGN_EXCHANGE_FEE_TRANSACTION);
        aftPayload.setLocalTransactionDateTime(getCurrentTransactionTime());
        aftPayload.setRetrievalReferenceNumber(RETRIEVAL_REFERENCE_NUMBER);
        aftPayload.setSystemsTraceAuditNumber(SYSTEMS_TRACE_AUDIT_NUMBER);
        aftPayload.setSurcharge(SURCHARGE);
        aftPayload.setSenderCurrencyCode(SENDER_CURRENCY_CODE);

        aftPayload.setAmount(paymentRequest.getTransactionDetails().getAmount());
        aftPayload.setCavv(senderDetails.getCavv());
        aftPayload.setSenderCardExpiryDate(senderDetails.getSenderCardExpiryDate());
        aftPayload.setSenderPrimaryAccountNumber(senderDetails.getSenderAccountNumber());

        return aftPayload;
    }

    private static String getCurrentTransactionTime() {
        return Singletons.INSTANCE.getDateFormat().format(new Date());
    }

}
