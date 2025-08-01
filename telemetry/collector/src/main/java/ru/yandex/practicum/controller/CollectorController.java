package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.HubEvent;
import ru.yandex.practicum.model.SensorEvent;
import ru.yandex.practicum.service.CollectorService;

@RestController
@RequestMapping(path = "events")
@RequiredArgsConstructor
@Slf4j
public class CollectorController {

    private final CollectorService collectorService;

    @PostMapping("/sensors")
    public void handleSensorEvent(@Valid @RequestBody SensorEvent sensorEvent) {
        log.info(String.format("Тело запроса: %s", sensorEvent));
        collectorService.handleSensorEvent(sensorEvent);
    }

    @PostMapping("/hubs")
    public void handleHubEvent(@Valid @RequestBody HubEvent hubEvent) {
        log.info(String.format("Тело запроса: %s", hubEvent));
        collectorService.handleHubEvent(hubEvent);
    }
}
