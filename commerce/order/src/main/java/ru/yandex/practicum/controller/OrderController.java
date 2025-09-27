package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    public List<OrderDto> getClientOrders(@RequestParam @NotBlank String username) {
        return orderService.getClientOrders(username);
    }

    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody NewOrderRequest request) {
        return orderService.createNewOrder(request);
    }

    @PostMapping("/{order-id}/pay")
    public OrderDto payOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.payment(orderId);
    }

    @PostMapping("/{order-id}/payment-failed")
    public OrderDto paymentFailed(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.paymentFailed(orderId);
    }

    @PostMapping("/{order-id}/delivery")
    public OrderDto delivery(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.delivery(orderId);
    }

    @PostMapping("/{order-id}/delivery-failed")
    public OrderDto deliveryFailed(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.deliveryFailed(orderId);
    }

    @PostMapping("/{order-id}/assembly")
    public OrderDto assembleOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.assembly(orderId);
    }

    @PostMapping("/{order-id}/assembly-failed")
    public OrderDto assemblyFailed(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.assemblyFailed(orderId);
    }

    @PostMapping("/return")
    public OrderDto returnOrder(@RequestBody @Valid ProductReturnRequest request) {
        return orderService.productReturn(request);
    }

    @PostMapping("/{order-id}/calculate/total")
    public OrderDto calculateTotalCost(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.calculateTotalCost(orderId);
    }

    @PostMapping("/{order-id}/calculate/delivery")
    public OrderDto calculateDeliveryCost(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.calculateDeliveryCost(orderId);
    }

    @PostMapping("/{order-id}/completed")
    public OrderDto completed(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId) {
        return orderService.completed(orderId);
    }
}
