package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class NewProductInWarehouseRequest {
    @org.hibernate.validator.constraints.UUID
    UUID productId;

    @NotNull
    Boolean fragile;

    @NotNull
    DimensionDto dimension;

    @Positive
    Double weight;
}