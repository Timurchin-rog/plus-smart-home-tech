package ru.yandex.practicum.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final Producer<Void, Object> producer;

    public <T> void send(String topic, T event) {
        try {
            while (true) {
                ProducerRecord<Void, Object> record = new ProducerRecord<>(topic, event);
                producer.send(record);
            }
        } catch (Exception e) {
            log.error("Ошибка отправки данных {}", topic);
        } finally {
            try {
                producer.flush();
            } finally {
                producer.close();
            }
        }
    }

}
