package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;

public class DeliveryMapper {

    public static DeliveryDto mapToDeliveryDto(Delivery delivery) {
        AddressDto addressDtoFrom = AddressMapper.mapToAddressDto(delivery.getFromAddress());
        AddressDto addressDtoTo = AddressMapper.mapToAddressDto(delivery.getToAddress());
        return DeliveryDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .fromAddress(addressDtoFrom)
                .toAddress(addressDtoTo)
                .status(delivery.getDeliveryState())
                .build();
    }

    public static Delivery mapFromRequest(DeliveryDto deliveryDto) {
        Address addressFrom = AddressMapper.mapFromRequest(deliveryDto.getFromAddress());
        Address addressTo = AddressMapper.mapFromRequest(deliveryDto.getToAddress());
        return Delivery.builder()
                .deliveryId(deliveryDto.getDeliveryId())
                .fromAddress(addressFrom)
                .toAddress(addressTo)
                .orderId(deliveryDto.getOrderId())
                .deliveryState(deliveryDto.getStatus())
                .build();
    }
}
