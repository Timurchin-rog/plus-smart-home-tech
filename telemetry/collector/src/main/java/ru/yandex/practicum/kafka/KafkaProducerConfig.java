package ru.yandex.practicum.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${collector.kafka.producer.properties.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${collector.kafka.producer.properties.key.serializer}")
    private String keySerializer;
    @Value("${collector.kafka.producer.properties.value.serializer}")
    private String valueSerializer;

    @Bean
    public ProducerFactory<Void, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<Void, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
