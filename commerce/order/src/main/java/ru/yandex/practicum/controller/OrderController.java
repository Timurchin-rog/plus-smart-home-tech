package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.order.NewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getClientOrders(@RequestParam String username) {
        return orderService.getClientOrders(username);
    }

    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody NewOrderRequest request) {
        return orderService.createNewOrder(request);
    }

    @PostMapping("/{orderId}/pay")
    public OrderDto payOrder(@PathVariable UUID orderId) {
        return orderService.payment(orderId);
    }

    @PostMapping("/{orderId}/payment-failed")
    public OrderDto paymentFailed(@PathVariable UUID orderId) {
        return orderService.paymentFailed(orderId);
    }

    @PostMapping("/{orderId}/delivery")
    public OrderDto delivery(@PathVariable UUID orderId) {
        return orderService.delivery(orderId);
    }

    @PostMapping("/{orderId}/delivery-failed")
    public OrderDto deliveryFailed(@PathVariable UUID orderId) {
        return orderService.deliveryFailed(orderId);
    }

    @PostMapping("/{orderId}/assembly")
    public OrderDto assembleOrder(@PathVariable UUID orderId) {
        return orderService.assembly(orderId);
    }

    @PostMapping("/{orderId}/assembly-failed")
    public OrderDto assemblyFailed(@PathVariable UUID orderId) {
        return orderService.assemblyFailed(orderId);
    }

    @PostMapping("/{orderId}/return")
    public OrderDto returnOrder(@PathVariable UUID orderId, @RequestBody ProductReturnRequest request) {
        return orderService.productReturn(request);
    }

    @PostMapping("/{orderId}/calculate/total")
    public OrderDto calculateTotalCost(@PathVariable UUID orderId) {
        return orderService.calculateTotalCost(orderId);
    }

    @PostMapping("/{orderId}/calculate/delivery")
    public OrderDto calculateDeliveryCost(@PathVariable UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @PostMapping("/{orderId}/completed")
    public OrderDto completed(@PathVariable UUID orderId) {
        return orderService.completed(orderId);
    }
}
