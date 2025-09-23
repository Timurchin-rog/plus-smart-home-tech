package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProductDto {

    @org.hibernate.validator.constraints.UUID
    UUID productId;

    @Positive
    Long quantity;

    @Positive
    BigDecimal price;
}
