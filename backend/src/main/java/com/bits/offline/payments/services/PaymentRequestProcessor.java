package com.bits.offline.payments.services;

import com.bits.offline.payments.controller.PaymentAcceptorController;
import com.bits.offline.payments.kafka.producer.MessagingService;
import com.bits.offline.payments.request.inbound.PaymentRequest;
import com.bits.offline.payments.request.outbound.aft.AFTPayload;
import com.bits.offline.payments.request.outbound.aft.AFTPayloadBuilder;
import com.bits.offline.payments.request.outbound.common.PaymentRequestBuilder;
import com.bits.offline.payments.request.outbound.common.VisaDirectPaymentRequest;
import com.bits.offline.payments.request.outbound.oct.OCTPayload;
import com.bits.offline.payments.request.outbound.oct.OCTPayloadBuilder;
import com.bits.offline.payments.sms.NotificationServices;
import com.bits.offline.payments.transport.VisaDirectClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentRequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRequestProcessor.class);

    private final VisaDirectClient visaDirectClient;
    private final NotificationServices notificationServices;

    @Autowired
    public PaymentRequestProcessor(final VisaDirectClient visaDirectClient,
                                   final NotificationServices notificationServices) {
        this.visaDirectClient = visaDirectClient;
        this.notificationServices = notificationServices;
    }

    public ResponseEntity processPaymentRequest(PaymentRequest paymentRequest) throws JsonProcessingException, JSONException {

        final VisaDirectPaymentRequest visaDirectPullPaymentRequest = PaymentRequestBuilder.createPullPaymentRequest();
        final AFTPayload aftpayload = AFTPayloadBuilder.createAFTPayload(paymentRequest);
        visaDirectPullPaymentRequest.setPayload(aftpayload.toJson());
        LOGGER.info("Triggering the AFT call");
        final ResponseEntity responseEntity = visaDirectClient.postToVisaDirect(visaDirectPullPaymentRequest);
        final JSONObject aftResponse = new JSONObject(responseEntity.getBody().toString());
        final Integer aftStatus = Integer.parseInt(aftResponse.getJSONObject("responseStatus").get("code").toString());
        LOGGER.trace(aftResponse.toString());
        LOGGER.info("AFT call completed with status code: {}", 200);

        final VisaDirectPaymentRequest visaDirectPushPaymentRequest = PaymentRequestBuilder.createPushPaymentRequest();
        final OCTPayload octPayload = OCTPayloadBuilder.createOCTPayload(paymentRequest);
        visaDirectPushPaymentRequest.setPayload(octPayload.toJson());
        LOGGER.info("Triggering the OCT call");
        final ResponseEntity finalResponseEntity = visaDirectClient.postToVisaDirect(visaDirectPushPaymentRequest);
        final JSONObject octResponse = new JSONObject(responseEntity.getBody().toString());
        final Integer octStatus = Integer.parseInt(octResponse.getJSONObject("responseStatus").get("code").toString());
        LOGGER.trace(finalResponseEntity.toString());
        LOGGER.info("OCT call completed with status code: {}", 200);


        notificationServices.sendNotification(paymentRequest);


        return ResponseEntity.status(HttpStatus.OK).build();

        /*if(octStatus == HttpStatus.OK.value()) {
            notificationServices.sendNotification(paymentRequest);
        } else {
            LOGGER.error("Transaction failed");
        }*/

        /*if(aftStatus == HttpStatus.OK.value()) {

        } else {
            LOGGER.error("Transaction failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }*/
    }
}
