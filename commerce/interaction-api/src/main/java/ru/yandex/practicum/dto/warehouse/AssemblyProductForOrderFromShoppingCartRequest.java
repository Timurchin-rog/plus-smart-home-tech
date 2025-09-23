package ru.yandex.practicum.dto.warehouse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssemblyProductForOrderFromShoppingCartRequest {

    @org.hibernate.validator.constraints.UUID
    UUID shoppingCartId;

    @org.hibernate.validator.constraints.UUID
    UUID orderId;
}
