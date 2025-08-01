package ru.yandex.practicum.service;

import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;

public interface CollectorService {

    void handleSensorEvent(SensorEvent sensorEvent);

    void handleHubEvent(HubEvent hubEvent);
}
