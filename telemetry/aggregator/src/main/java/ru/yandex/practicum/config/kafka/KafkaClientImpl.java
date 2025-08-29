package ru.yandex.practicum.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaClientImpl implements KafkaClient {

    private final KafkaConfig kafkaConfig;

    @Override
    @Bean
    public Producer<Void, SpecificRecordBase> getProducer() {
        return new KafkaProducer<>(kafkaConfig.getProducerProperties());
    }

    @Override
    @Bean
    public Consumer<Void, SpecificRecordBase> getConsumer() {
        return new KafkaConsumer<>(kafkaConfig.getConsumerProperties());
    }

}
