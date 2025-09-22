package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public DeliveryDto planDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.planDelivery(deliveryDto);
    }

    @PostMapping("/picked")
    public void deliveryPicked(@RequestParam UUID deliveryId) {
        deliveryService.deliveryPicked(deliveryId);
    }

    @PostMapping("/failed")
    public void deliveryFailed(@RequestParam UUID orderId) {
        deliveryService.deliveryFailed(orderId);
    }

    @PostMapping("/successful")
    public void deliverySuccess(@RequestParam UUID orderId) {
        deliveryService.deliverySuccessful(orderId);
    }

    @PostMapping("/cost")
    public BigDecimal deliveryCost(@RequestBody OrderDto orderDto) {
        return deliveryService.deliveryCost(orderDto);
    }
}
