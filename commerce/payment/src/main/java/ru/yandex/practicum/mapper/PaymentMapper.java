package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.model.Payment;

public class PaymentMapper {

    public static PaymentDto mapToPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .orderId(payment.getOrderId())
                .productsPrice(payment.getProductTotal())
                .deliveryPrice(payment.getDeliveryTotal())
                .totalPrice(payment.getTotalPayment())
                .status(payment.getState())
                .build();
    }

}
