package ru.yandex.practicum.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Properties;

@Configuration
public class KafkaClientConfig {

    @Bean
    @ConfigurationProperties(prefix = "collector.kafka.producer.properties")
    public Properties getProducerProperties() {
        return new Properties();
    }

    @Bean
    @ConfigurationProperties(prefix = "collector.kafka.consumer.properties")
    public Properties getConsumerProperties() {
        return new Properties();
    }

    @Bean
    KafkaClient getKafkaClient() {
        return new KafkaClient() {
            private Producer<Void, SpecificRecordBase> kafkaProducer;
            private Consumer<Void, SpecificRecordBase> kafkaConsumer;

            @Override
            public Producer<Void, SpecificRecordBase> getProducer() {
                kafkaProducer = new KafkaProducer<>(getProducerProperties());
                return kafkaProducer;
            }

            @Override
            public Consumer<Void, SpecificRecordBase> getConsumer() {
                kafkaConsumer = new KafkaConsumer<>(getConsumerProperties());
                return kafkaConsumer;
            }

            @Override
            public void close() {
                try {
                    kafkaProducer.flush();
                    kafkaConsumer.commitSync();
                } finally {
                    kafkaProducer.close(Duration.ofSeconds(10));
                    kafkaConsumer.close();
                }
            }
        };
    }
}
