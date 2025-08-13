package ru.yandex.practicum.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventProducer {

    private final KafkaTemplate<Void, Object> kafkaTemplate;

    public KafkaEventProducer(KafkaTemplate<Void, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> void send(String topic, T event) {
            kafkaTemplate.send(topic, event);
    }

}
