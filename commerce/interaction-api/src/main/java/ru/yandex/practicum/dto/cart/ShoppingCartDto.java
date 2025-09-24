package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingCartDto {
    @org.hibernate.validator.constraints.UUID
    UUID shoppingCartId;

    @NotEmpty
    Map<UUID, Long> products;
}
