package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.OrderProductDto;
import ru.yandex.practicum.model.Order;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .shoppingCartId(order.getShoppingCartId())
                .products(mapProducts(order.getProducts()))
                .status(order.getState())
                .deliveryId(order.getDeliveryId())
                .paymentId(order.getPaymentId())
                .totalPrice(order.getTotalPrice())
                .productsPrice(order.getProductPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .build();
    }

    private static List<OrderProductDto> mapProducts(Map<UUID, Integer> products) {
        if (products == null) return Collections.emptyList();
        return products.entrySet().stream()
                .map(e -> new OrderProductDto())
                .collect(Collectors.toList());
    }
}
