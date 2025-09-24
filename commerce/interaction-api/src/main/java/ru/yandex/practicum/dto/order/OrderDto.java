package ru.yandex.practicum.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @org.hibernate.validator.constraints.UUID
    UUID orderId;

    @org.hibernate.validator.constraints.UUID
    UUID shoppingCartId;

    @NotEmpty
    List<OrderProductDto> products;

    @NotNull
    OrderState status;

    @org.hibernate.validator.constraints.UUID
    UUID deliveryId;

    @org.hibernate.validator.constraints.UUID
    UUID paymentId;

    @Positive
    Double totalWeight;
    @Positive
    Double totalVolume;
    @Positive
    Boolean fragile;

    @Positive
    BigDecimal totalPrice;
    @Positive
    BigDecimal productsPrice;
    @Positive
    BigDecimal deliveryPrice;
}
