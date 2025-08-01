package ru.yandex.practicum.service;

import ru.yandex.practicum.grpc.telemetry.event.HubEvent;
import ru.yandex.practicum.grpc.telemetry.event.SensorEvent;

public interface CollectorService {

    void handleSensorEvent(SensorEvent sensorEvent);

    void handleHubEvent(HubEvent hubEvent);
}
