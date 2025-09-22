package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.order.OrderState;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {

    @NotNull
    UUID orderId;

    @NotNull
    UUID shoppingCartId;

    @NotNull
    List<OrderProductDto> products;

    @NotNull
    OrderState status;

    UUID deliveryId;

    UUID paymentId;

    Double totalWeight;
    Double totalVolume;
    Boolean fragile;

    BigDecimal totalPrice;
    BigDecimal productsPrice;
    BigDecimal deliveryPrice;
}
