package ru.yandex.practicum.dto.order;

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
public class OrderBookingDto {

    @org.hibernate.validator.constraints.UUID
    UUID productId;

    @Positive
    Long quantity;
}
