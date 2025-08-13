package ru.yandex.practicum.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.producer.Producer;

public interface KafkaClient extends AutoCloseable {
    Producer<Void, SpecificRecordBase> getProducer();

    Consumer<Void, SpecificRecordBase> getConsumer();
}
