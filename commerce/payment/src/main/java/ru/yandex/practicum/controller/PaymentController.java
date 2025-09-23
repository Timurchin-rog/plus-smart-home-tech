package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    PaymentDto payment(@Valid @RequestBody OrderDto orderDto) {
        return paymentService.payment(orderDto);
    }

    @PostMapping("/total-cost")
    BigDecimal getTotalCost(@Valid @RequestBody OrderDto orderDto) {
        return paymentService.getTotalCost(orderDto);
    }

    @PostMapping("/success")
    void paymentSuccess(@RequestParam @org.hibernate.validator.constraints.UUID UUID paymentId) {
        paymentService.paymentSuccess(paymentId);
    }

    @PostMapping("/product-cost")
    BigDecimal productCost(@Valid @RequestBody OrderDto orderDto) {
        return paymentService.productCost(orderDto);
    }

    @PostMapping("failed")
    void paymentFailed(@RequestParam @org.hibernate.validator.constraints.UUID UUID paymentId) {
        paymentService.paymentFailed(paymentId);
    }
}
