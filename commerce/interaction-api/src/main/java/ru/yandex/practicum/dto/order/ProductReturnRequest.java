package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductReturnRequest {

    @org.hibernate.validator.constraints.UUID
    UUID orderId;

    @NotNull @NotEmpty
    Map<UUID, Long> products;
}
