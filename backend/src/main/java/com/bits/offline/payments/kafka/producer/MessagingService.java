package com.bits.offline.payments.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
@PropertySource("classpath:producer.properties")
public class MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingService.class);

    private final Producer<String, String> kafkaProducer;

    public MessagingService(@Value("${acks}") String acks,
                            @Value("#{'${bootstrap.servers}'.split(',')}") List<String> bootStrapServers,
                            @Value("${client.id}") String clientId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootStrapServers);
        props.put("acks", acks);
        props.put("client.id", clientId);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer<>(props);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaProducer::close));
    }

    public RecordMetadata pushToKafka(final String topic, final String message) throws ExecutionException, InterruptedException {
        try {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);
            return kafkaProducer.send(producerRecord).get();
        } finally {
            LOGGER.info("pushing into kafka completed in the topic: {}, in the message: {}", topic, message);
        }
    }

}
