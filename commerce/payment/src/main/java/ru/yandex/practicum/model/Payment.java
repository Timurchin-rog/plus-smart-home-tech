package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.payment.PaymentState;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id", updatable = false, nullable = false)
    UUID paymentId;

    @Column(name = "order_id", nullable = false)
    UUID orderId;

    @Column(name = "product_total", nullable = false, precision = 15, scale = 2)
    BigDecimal productTotal;

    @Column(name = "delivery_total", nullable = false, precision = 15, scale = 2)
    BigDecimal deliveryTotal;

    @Column(name = "total_payment", nullable = false, precision = 15, scale = 2)
    BigDecimal totalPayment;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_state", nullable = false)
    PaymentState state;
}
