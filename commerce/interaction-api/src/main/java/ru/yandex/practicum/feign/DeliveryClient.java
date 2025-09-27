package ru.yandex.practicum.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "delivery-service", path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping
    DeliveryDto planDelivery(@RequestBody @Valid DeliveryDto deliveryDto);

    @PostMapping("/cost")
    BigDecimal deliveryCost(@RequestBody @Valid OrderDto orderDto);

    @PostMapping("/{delivery-id}/success")
    void deliverySuccess(@PathVariable(name = "delivery-id") @org.hibernate.validator.constraints.UUID UUID deliveryId);

    @PostMapping("/{delivery-id}/failed")
    void deliveryFailed(@PathVariable(name = "delivery-id") @org.hibernate.validator.constraints.UUID UUID deliveryId);

    @PostMapping("/{order-id}/shipped")
    void shippedToDelivery(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId,
                           @RequestParam @org.hibernate.validator.constraints.UUID UUID deliveryId);
}
