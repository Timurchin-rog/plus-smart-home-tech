package ru.yandex.practicum.dto.warehouse;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippedToDeliveryRequest {

    @org.hibernate.validator.constraints.UUID
    UUID orderId;

    @org.hibernate.validator.constraints.UUID
    UUID deliveryId;
}
