package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.order.OrderState;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID orderId;

    @Column(nullable = false)
    UUID shoppingCartId;

    @Column(nullable = false)
    String username;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    Map<UUID, Integer> products;

    @Column(name = "payment_id")
    UUID paymentId;

    @Column(name = "delivery_id")
    UUID deliveryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_state")
    OrderState state;

    @Column(name = "delivery_weight")
    double deliveryWeight;

    @Column(name = "delivery_volume")
    double deliveryVolume;

    @Column(name = "fragile")
    boolean fragile;

    @Column(name = "total_price", precision = 15, scale = 2)
    BigDecimal totalPrice;

    @Column(name = "delivery_price", precision = 15, scale = 2)
    BigDecimal deliveryPrice;

    @Column(name = "product_price", precision = 15, scale = 2)
    BigDecimal productPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "from_address_id", nullable = false)
    Address fromAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_address_id", nullable = false)
    Address toAddress;
}
