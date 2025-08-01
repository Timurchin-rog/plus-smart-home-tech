package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.HubEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEvent;
import ru.yandex.practicum.kafka.KafkaProducer;

@Service
@RequiredArgsConstructor
public class CollectorServiceImpl implements CollectorService {

    @Override
    public void handleSensorEvent(SensorEvent sensorEvent) {
        KafkaProducer.send("telemetry.sensors.v1", sensorEvent);
    }

    @Override
    public void handleHubEvent(HubEvent hubEvent) {
        KafkaProducer.send("telemetry.hubs.v1", hubEvent);
    }
}
