package ru.yandex.practicum.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.VoidSerializer;

import java.util.Properties;

public class KafkaProducer {

    public static void send(String topic, Object event) {
        Properties config = getProducerProperties();
        try (org.apache.kafka.clients.producer.KafkaProducer<Void, Object> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(config)) {
            ProducerRecord<Void, Object> record = new ProducerRecord<>(topic, event);
            producer.send(record);
        }
    }

    private static Properties getProducerProperties() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GeneralAvroSerializer.class);
        return properties;
    }
}
