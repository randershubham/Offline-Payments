package com.bits.offline.payments.controller;

import com.bits.offline.payments.kafka.producer.MessagingService;
import com.bits.offline.payments.request.inbound.PaymentRequest;
import com.bits.offline.payments.request.outbound.aft.AFTPayload;
import com.bits.offline.payments.request.outbound.common.VisaDirectPaymentRequest;
import com.bits.offline.payments.request.outbound.common.PaymentRequestBuilder;
import com.bits.offline.payments.request.outbound.aft.AFTPayloadBuilder;
import com.bits.offline.payments.request.outbound.oct.OCTPayload;
import com.bits.offline.payments.request.outbound.oct.OCTPayloadBuilder;
import com.bits.offline.payments.services.PaymentRequestProcessor;
import com.bits.offline.payments.transport.VisaDirectClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by Vithun on 21-Jan-18.
 */
@RestController
@PropertySource(value = {"classpath:application.properties"})
public class PaymentAcceptorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentAcceptorController.class);

    private final VisaDirectClient visaDirectClient;
    private final MessagingService messagingService;
    private final PaymentRequestProcessor paymentRequestProcessor;

    @Value("${producingTopic}")
    private String topic;

    @Autowired
    public PaymentAcceptorController(final VisaDirectClient visaDirectClient,
                                     final MessagingService messagingService,
                                     final PaymentRequestProcessor paymentRequestProcessor) {
        this.visaDirectClient = visaDirectClient;
        this.messagingService = messagingService;
        this.paymentRequestProcessor = paymentRequestProcessor;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    @RequestMapping(value="/test", method = RequestMethod.GET)
    @ResponseBody
    public String helloWord() {
        System.out.println("Test message");
        return "Test message";
    }

    @RequestMapping(value="/payments/pull", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public ResponseEntity pullPayment(String requestPayload) throws JsonProcessingException {
        VisaDirectPaymentRequest visaDirectPaymentRequest = PaymentRequestBuilder.createPullPaymentRequest();
        AFTPayload aftpayload = AFTPayloadBuilder.createAFTPayload(requestPayload);
        visaDirectPaymentRequest.setPayload(aftpayload.toJson());
        return visaDirectClient.postToVisaDirect(visaDirectPaymentRequest);
    }

    @RequestMapping(value="/payments/push", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public ResponseEntity pushPayment(String requestPayload) throws JsonProcessingException {
        VisaDirectPaymentRequest visaDirectPaymentRequest = PaymentRequestBuilder.createPushPaymentRequest();
        OCTPayload octPayload = OCTPayloadBuilder.createOCTPayload(requestPayload);
        visaDirectPaymentRequest.setPayload(octPayload.toJson());
        return visaDirectClient.postToVisaDirect(visaDirectPaymentRequest);
    }

    @RequestMapping(value="/payments", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity pushPayment(@RequestBody PaymentRequest paymentRequest) throws JsonProcessingException {

        String inputRequest = paymentRequest.toJson();
        LOGGER.info("The input request is: {}", inputRequest);
        return paymentRequestProcessor.processPaymentRequest(paymentRequest);
    }

    @RequestMapping(value="/payments/kafka", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity pushPaymentKafka(@RequestBody PaymentRequest paymentRequest) throws JsonProcessingException, ExecutionException, InterruptedException {

        String inputRequest = paymentRequest.toJson();
        LOGGER.info("The input request is: {}", inputRequest);

        RecordMetadata recordMetadata = messagingService.pushToKafka(topic, inputRequest);
        if(recordMetadata.hasOffset()) {
            return ResponseEntity.ok("Transaction initiated and will be processed in a flash");
        } else {
            LOGGER.warn("Unable to able to kafka for the message: {}, and the record metadata is: {}", inputRequest, recordMetadata);
            return ResponseEntity.status(500).body("Not able to push to kafka");
        }

    }



    /*public static void main(String[] args) throws JsonProcessingException {
        VisaDirectPaymentRequest visaDirectPaymentRequest = PaymentRequestBuilder.createDefaultPaymentRequest();
        AFTPayload visaDirectPayload = AFTPayloadBuilder.createAFTPayload("requestPayload");
        visaDirectPaymentRequest.setPayload(visaDirectPayload.toJson());
        System.out.println(visaDirectPaymentRequest.toJson());

        VisaDirectClient visaDirectClient = new VisaDirectClient();
        System.out.println(visaDirectClient.pushTransaction(visaDirectPaymentRequest.toJson()).getBody());

    }*/

    /*public static void main(String[] args) throws JsonProcessingException {
        VisaDirectPaymentRequest visaDirectPaymentRequest = PaymentRequestBuilder.createPushPaymentRequest();
        OCTPayload octPayload = OCTPayloadBuilder.createOCTPayload("requestPayload");
        visaDirectPaymentRequest.setPayload(octPayload.toJson());

        System.out.println(visaDirectPaymentRequest.toJson());

        VisaDirectClient visaDirectClient = new VisaDirectClient();
        System.out.println(visaDirectClient.postToVisaDirect(visaDirectPaymentRequest.toJson()).getBody());
    }*/
}
