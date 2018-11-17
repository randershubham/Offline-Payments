package com.bits.offline.payments.sms;

import com.bits.offline.payments.request.inbound.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class NotificationServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServices.class);

    private static final Long DIVIDER = 1000000000000L;

    private static final boolean IS_NOTIFICATION_ENABLED = Boolean.parseBoolean(System.getProperty("notificationEnabled"));

    private String sendSms(final String message, final String number) {
        try {
            // Write your own sendSms module, we have written our own
            return "dummyMessage";
        } catch (Exception e) {
            LOGGER.error("Error SMS {}", e);
            return "Error " + e.getMessage();
        }
    }

    public void sendNotification(final PaymentRequest paymentRequest) {

        if(!IS_NOTIFICATION_ENABLED) {
            LOGGER.info("Notification service is disabled, so no sms will be sent, if need to send sms star the service with JVM property: 'notificationEnabled' as true");
            return;
        }

        final String amount = paymentRequest.getTransactionDetails().getAmount();
        final String senderNumber = paymentRequest.getSenderDetails().getSenderMobileNumber();
        final String recipientNumber = paymentRequest.getRecipientDetails().getRecipientMobileNumber();

        final Long senderLast4 = Long.parseLong(paymentRequest.getSenderDetails().getSenderAccountNumber()) / DIVIDER;
        final Long recipientLast4 = Long.parseLong(paymentRequest.getRecipientDetails().getRecipientPrimaryAccountNumber()) / DIVIDER;

        final String senderTransactionMessage = "The amount " + amount + " has been debited from your account ending with " + senderLast4;
        final String recipientTransactionMessage = "The amount " + amount + " has been credited from your account ending with " + recipientLast4;

        //Sending sms to recepient
        String recipientOutput = sendSms(recipientTransactionMessage, recipientNumber);
        LOGGER.info("SMS output send for recipient is  {}", recipientOutput);

        //Sending sms to recepient
        String senderOutput = sendSms(senderTransactionMessage, senderNumber);
        LOGGER.info("SMS output send for sender {}", senderOutput);

    }
}
