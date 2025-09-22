package ru.yandex.practicum.dto.payment;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.payment.PaymentState;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {

    @NotNull
    UUID paymentId;

    @NotNull
    UUID orderId;

    @NotNull
    BigDecimal productsPrice;

    @NotNull
    BigDecimal deliveryPrice;

    @NotNull
    BigDecimal totalPrice;

    @NotNull
    PaymentState status;
}
