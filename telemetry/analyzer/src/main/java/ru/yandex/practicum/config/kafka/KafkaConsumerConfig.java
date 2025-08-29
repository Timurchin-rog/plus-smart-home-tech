package ru.yandex.practicum.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaConfig kafkaConfig;

    @Bean
    public Consumer<Void, HubEventAvro> getHubConsumer() {
        return new KafkaConsumer<>(kafkaConfig.getConsumerHubProperties());
    }

    @Bean
    public Consumer<Void, SensorsSnapshotAvro> getSnapshotConsumer() {
        return new KafkaConsumer<>(kafkaConfig.getConsumerSnapshotProperties());
    }
}
