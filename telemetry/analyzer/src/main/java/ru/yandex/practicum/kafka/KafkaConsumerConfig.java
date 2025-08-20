package ru.yandex.practicum.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    @ConfigurationProperties(prefix = "kafka.hub-event-consumer.properties")
    public Properties getHubEventConsumerProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka.snapshot-consumer.properties")
    public Properties getSnapshotConsumerProperties() {
        return new Properties();
    }

    @Bean
    public Consumer<Void, HubEventAvro> getHubEventConsumer() {
        return new KafkaConsumer<>(getHubEventConsumerProperties());
    }

    @Bean
    public Consumer<Void, SensorsSnapshotAvro> getSnapshotConsumer() {
        return new KafkaConsumer<>(getSnapshotConsumerProperties());
    }
}
