package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.enums.delivery.DeliveryState;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderClient orderClient;
    private final WarehouseClient warehouseClient;

    private static BigDecimal costByAddress(String warehouseAddress, BigDecimal BASE_RATE) {
        final String ADDRESS_1 = "ADDRESS_1";
        final String ADDRESS_2 = "ADDRESS_2";

        BigDecimal warehouseMultiplier = BigDecimal.ZERO;

        if (warehouseAddress.contains(ADDRESS_1)) {
            warehouseMultiplier = warehouseMultiplier.add(BigDecimal.ONE);
        }

        if (warehouseAddress.contains(ADDRESS_2)) {
            warehouseMultiplier = warehouseMultiplier.add(BigDecimal.valueOf(2));
        }

        return BASE_RATE.multiply(warehouseMultiplier).add(BASE_RATE);
    }

    @Override
    @Transactional
    public DeliveryDto planDelivery(DeliveryDto deliveryDto) {
        log.info("[Доставка] Планирование новой доставки: {}", deliveryDto);

        deliveryDto.setStatus(DeliveryState.CREATED);

        Delivery delivery = DeliveryMapper.mapFromRequest(deliveryDto);
        deliveryRepository.save(delivery);

        log.info("[Доставка] Доставка {} успешно создана", delivery.getDeliveryId());
        return DeliveryMapper.mapToDeliveryDto(delivery);
    }

    @Override
    @Transactional
    public void deliverySuccessful(UUID orderId) {
        log.info("[Доставка] Завершение доставки для заказа {}", orderId);

        Delivery delivery = checkDelivery(orderId);

        delivery.setDeliveryState(DeliveryState.DELIVERED);
        deliveryRepository.save(delivery);

        orderClient.delivery(delivery.getOrderId());
        log.info("[Доставка] Доставка {} успешно завершена", delivery.getDeliveryId());
    }

    @Override
    @Transactional
    public void deliveryFailed(UUID orderId) {
        log.info("[Доставка] Ошибка доставки для заказа {}", orderId);

        Delivery delivery = checkDelivery(orderId);

        delivery.setDeliveryState(DeliveryState.FAILED);
        deliveryRepository.save(delivery);

        orderClient.failDelivery(delivery.getOrderId());
        log.info("[Доставка] Доставка {} помечена как неуспешная", delivery.getDeliveryId());
    }

    @Override
    @Transactional
    public void deliveryPicked(UUID deliveryId) {
        log.info("[Доставка] Получение товара для доставки: {}", deliveryId);

        Delivery delivery = checkDelivery(deliveryId);

        delivery.setDeliveryState(DeliveryState.IN_DELIVERY);
        deliveryRepository.save(delivery);

        ShippedToDeliveryRequest request = ShippedToDeliveryRequest.builder()
                .orderId(delivery.getOrderId())
                .deliveryId(deliveryId)
                .build();

        warehouseClient.shippedToDelivery(request);
        log.info("[Доставка] Товар передан в доставку {}", deliveryId);
    }

    @Override
    @Transactional
    public BigDecimal deliveryCost(OrderDto orderDto) {
        UUID orderId = orderDto.getOrderId();
        log.info("[Доставка] Расчёт стоимости доставки для заказа {}", orderId);

        Delivery delivery = checkDelivery(orderId);

        AddressDto warehouseAddressDto = warehouseClient.getAddress();
        String warehouseAddress = warehouseAddressDto.getStreet();

        final BigDecimal BASE_RATE = BigDecimal.valueOf(5.0);
        BigDecimal step1 = costByAddress(warehouseAddress, BASE_RATE);

        BigDecimal fragileAddition = Boolean.TRUE.equals(orderDto.getFragile())
                ? step1.multiply(BigDecimal.valueOf(0.2))
                : BigDecimal.ZERO;
        BigDecimal step2 = step1.add(fragileAddition);

        BigDecimal weightAddition = BigDecimal.valueOf(orderDto.getTotalWeight())
                .multiply(BigDecimal.valueOf(0.3));
        BigDecimal step3 = step2.add(weightAddition);

        BigDecimal volumeAddition = BigDecimal.valueOf(orderDto.getTotalVolume())
                .multiply(BigDecimal.valueOf(0.2));
        BigDecimal step4 = step3.add(volumeAddition);

        String deliveryStreet = delivery.getToAddress().getStreet();
        BigDecimal addressAddition = warehouseAddress.equals(deliveryStreet)
                ? BigDecimal.ZERO
                : step4.multiply(BigDecimal.valueOf(0.2));
        BigDecimal totalCost = step4.add(addressAddition);

        log.info("[Доставка] Стоимость доставки для заказа {}: {}", orderId, totalCost);
        return totalCost;
    }

    private Delivery checkDelivery(UUID orderId) {
        return deliveryRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException(String.format("Доставка id = %s не найдена", orderId))
        );
    }
}
