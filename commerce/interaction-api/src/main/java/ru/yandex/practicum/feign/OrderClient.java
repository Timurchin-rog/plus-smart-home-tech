package ru.yandex.practicum.feign;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.dto.order.OrderDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service", path = "/api/v1/orders")
public interface OrderClient {

    @PostMapping
    OrderDto createOrder(@RequestParam @org.hibernate.validator.constraints.UUID UUID shoppingCartId);

    @PostMapping("/{order-id}/pay")
    OrderDto payOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @GetMapping("/{order-id}/total-cost")
    BigDecimal calculateTotalCost(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @GetMapping("/{order-id}/delivery-cost")
    BigDecimal calculateDeliveryCost(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @GetMapping
    List<OrderDto> getOrders(@RequestParam @NotBlank String username);

    @PostMapping("/{order-id}/assemble")
    void assembleOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/ship")
    void shipOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId,
                   @RequestParam @org.hibernate.validator.constraints.UUID UUID deliveryId);

    @PostMapping("/{order-id}/return")
    void returnOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/fail-payment")
    void failPayment(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/fail-delivery")
    void failDelivery(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/fail-assembly")
    void failAssembly(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/complete")
    OrderDto completeOrder(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);

    @PostMapping("/{order-id}/delivered")
    OrderDto delivery(@PathVariable(name = "order-id") @org.hibernate.validator.constraints.UUID UUID orderId);
}
