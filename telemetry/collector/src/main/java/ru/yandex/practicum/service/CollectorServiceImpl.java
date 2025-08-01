package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;

@Service
@RequiredArgsConstructor
public class CollectorServiceImpl implements CollectorService {

    private final KafkaAvroProducer kafkaAvroProducer;

    @Override
    public void handleSensorEvent(SensorEvent sensorEvent) {
        kafkaAvroProducer.send("telemetry.sensors.v1", sensorEvent);
    }

    @Override
    public void handleHubEvent(HubEvent hubEvent) {
        kafkaAvroProducer.send("telemetry.hubs.v1", hubEvent);
    }
}
