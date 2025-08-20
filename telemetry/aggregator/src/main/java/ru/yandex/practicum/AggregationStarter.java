package ru.yandex.practicum;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.KafkaClient;
import ru.yandex.practicum.service.AggregatorService;

import java.time.Duration;
import java.util.List;

@Component
public class AggregationStarter implements CommandLineRunner {

    private final Producer<Void, SpecificRecordBase> producer;
    private final Consumer<Void, SpecificRecordBase> consumer;
    private final AggregatorService aggregatorService;
    @Value("${kafka.topics.sensors-events}")
    private String sensorsTopic;

    public AggregationStarter(KafkaClient kafkaClient, AggregatorService aggregatorService) {
        this.producer = kafkaClient.getProducer();
        this.consumer = kafkaClient.getConsumer();
        this.aggregatorService = aggregatorService;
    }

    @Override
    public void run(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
        try {
            consumer.subscribe(List.of(sensorsTopic));
            while (true) {
                ConsumerRecords<Void, SpecificRecordBase> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<Void, SpecificRecordBase> record : records) {
                    aggregatorService.aggregationSnapshot(producer, record.value());
                }
                consumer.commitAsync();
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {

        } finally {
            try {
                producer.flush();
                consumer.commitSync();
            } finally {
                consumer.close();
                producer.close(Duration.ofSeconds(5));
            }
        }
    }
}