package com.bits.offline.payments.kafka.consumer;

import com.bits.offline.payments.request.inbound.PaymentRequest;
import com.bits.offline.payments.services.PaymentRequestProcessor;
import com.bits.offline.payments.utils.Singletons;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
@PropertySource(value = {"classpath:consumer.properties", "classpath:application.properties"})
public class MessageConsumingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumingService.class);

    private final KafkaConsumer<String, String> kafkaConsumer;
    private final PaymentRequestProcessor paymentRequestProcessor;

    @Autowired
    public MessageConsumingService(@Value("#{'${bootstrap.servers}'.split(',')}") List<String> bootStrapServers,
                                   @Value("${group.id}") String groupId,
                                   @Value("${client.id}") String clientId,
                                   @Value("#{'${consumingTopics}'.split(',')}") List<String> topics,
                                   final PaymentRequestProcessor paymentRequestProcessor) {

        this.paymentRequestProcessor = paymentRequestProcessor;

        // Initializing kafka consumer
        Properties props = new Properties();
        props.put("bootstrap.servers", bootStrapServers);
        props.put("client.id", clientId + "." + topics);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(topics);
    }

    @PostConstruct
    public void init() {
        Thread thread = new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    consumerRecordHandler(consumerRecord);
                }
            }
        });
        thread.start();
    }

    public void consumerRecordHandler(ConsumerRecord<String, String> consumerRecord) {

        try {
            PaymentRequest paymentRequest = Singletons.INSTANCE.getObjectMapper().readValue(consumerRecord.value(), PaymentRequest.class);
            LOGGER.info("Received from kafka: {}", paymentRequest.toJson());
            paymentRequestProcessor.processPaymentRequest(paymentRequest);
        } catch (IOException e) {
            LOGGER.error("Unable to create paymentRequest from consumerRecord: {}", consumerRecord.value());
        } finally {
            LOGGER.info("Transaction completed");
        }

    }
}
