package ru.yandex.practicum.dto.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @org.hibernate.validator.constraints.UUID
    UUID paymentId;

    @org.hibernate.validator.constraints.UUID
    UUID orderId;

    @Positive
    BigDecimal productsPrice;

    @Positive
    BigDecimal deliveryPrice;

    @Positive
    BigDecimal totalPrice;

    @Positive
    PaymentState status;
}
