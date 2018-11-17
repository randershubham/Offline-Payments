package com.bits.offline.payments.request.outbound.common;

public abstract class PaymentRequestBuilder {

    private static final String AUTH_METHOD = "wsi_client_cert";
    private static final String METHOD = "POST";
    private static final String PULL_FUND_TRANSACTION_PATH = "/visadirect/fundstransfer/v1/pullfundstransactions";
    private static final String PULL_FUND_URL = "https://sandbox.api.visa.com/visadirect/fundstransfer/v1/pullfundstransactions";
    private static final String PUSH_FUND_URL = "https://sandbox.api.visa.com/visadirect/fundstransfer/v1/pushfundstransactions";
    private static final String PUSH_FUND_TRANSACTION_PATH = "/visadirect/fundstransfer/v1/pushfundstransactions";
    private static final String PRODUCT_SLUG = "visa_direct";
    private static final String MEDIA_TYPE = "JSON";

    private PaymentRequestBuilder() {
    }

    public static VisaDirectPaymentRequest createPullPaymentRequest() {
        VisaDirectPaymentRequest visaDirectPaymentRequest = new VisaDirectPaymentRequest();
        visaDirectPaymentRequest.setAuthMethod(AUTH_METHOD);
        visaDirectPaymentRequest.setMethod(METHOD);
        visaDirectPaymentRequest.setMediaType(MEDIA_TYPE);
        visaDirectPaymentRequest.setPath(PULL_FUND_TRANSACTION_PATH);
        visaDirectPaymentRequest.setProductSlug(PRODUCT_SLUG);
        visaDirectPaymentRequest.setUrl(PULL_FUND_URL);
        return visaDirectPaymentRequest;
    }

    public static VisaDirectPaymentRequest createPushPaymentRequest() {
        VisaDirectPaymentRequest visaDirectPaymentRequest = new VisaDirectPaymentRequest();
        visaDirectPaymentRequest.setAuthMethod(AUTH_METHOD);
        visaDirectPaymentRequest.setMethod(METHOD);
        visaDirectPaymentRequest.setMediaType(MEDIA_TYPE);
        visaDirectPaymentRequest.setPath(PUSH_FUND_TRANSACTION_PATH);
        visaDirectPaymentRequest.setProductSlug(PRODUCT_SLUG);
        visaDirectPaymentRequest.setUrl(PUSH_FUND_URL);
        return visaDirectPaymentRequest;
    }

}