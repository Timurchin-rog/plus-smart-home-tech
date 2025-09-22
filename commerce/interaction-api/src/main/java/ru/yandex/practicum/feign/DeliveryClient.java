package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "delivery-service", path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping
    DeliveryDto planDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/cost")
    BigDecimal deliveryCost(@RequestBody OrderDto orderDto);

    @PostMapping("/{deliveryId}/success")
    void deliverySuccess(@PathVariable UUID deliveryId);

    @PostMapping("/{deliveryId}/failed")
    void deliveryFailed(@PathVariable UUID deliveryId);

    @PostMapping("/{orderId}/shipped")
    void shippedToDelivery(@PathVariable UUID orderId, @RequestParam UUID deliveryId);
}
