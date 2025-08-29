package ru.yandex.practicum.config.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;

public interface KafkaClient {
    Producer<Void, SpecificRecordBase> getProducer();

    Consumer<Void, SpecificRecordBase> getConsumer();
}
