package ru.yandex.practicum.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.payment.PaymentDto;

import java.math.BigDecimal;
import java.util.UUID;

@FeignClient(name = "payment-service", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping("/product-cost")
    BigDecimal productCost(@Valid @RequestBody OrderDto orderDto);

    @PostMapping("/total-cost")
    BigDecimal getTotalCost(@Valid @RequestBody OrderDto orderDto);

    @PostMapping
    PaymentDto payment(@Valid @RequestBody OrderDto orderDto);

    @PostMapping("/{payment-id}/success")
    void paymentSuccess(@PathVariable(name = "payment-id") @org.hibernate.validator.constraints.UUID UUID paymentId);

    @PostMapping("/{payment-id}/failed")
    void paymentFailed(@PathVariable(name = "payment-id") @org.hibernate.validator.constraints.UUID UUID paymentId);
}
