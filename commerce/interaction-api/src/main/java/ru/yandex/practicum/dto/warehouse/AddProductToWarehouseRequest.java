package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddProductToWarehouseRequest {
    @org.hibernate.validator.constraints.UUID
    UUID productId;

    @Positive
    Integer quantity;
}
