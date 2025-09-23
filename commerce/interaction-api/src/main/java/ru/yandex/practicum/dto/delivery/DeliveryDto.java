package ru.yandex.practicum.dto.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.enums.delivery.DeliveryState;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryDto {

    @org.hibernate.validator.constraints.UUID
    UUID deliveryId;

    @org.hibernate.validator.constraints.UUID
    UUID orderId;

    @NotNull
    AddressDto fromAddress;

    @NotNull
    AddressDto toAddress;

    @NotNull
    DeliveryState status;
}
