package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.delivery.DeliveryDto;
import ru.yandex.practicum.dto.order.NewOrderRequest;
import ru.yandex.practicum.dto.order.OrderDto;
import ru.yandex.practicum.dto.order.ProductReturnRequest;
import ru.yandex.practicum.dto.payment.PaymentDto;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductForOrderFromShoppingCartRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.enums.delivery.DeliveryState;
import ru.yandex.practicum.enums.order.OrderState;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.feign.DeliveryClient;
import ru.yandex.practicum.feign.PaymentClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.AddressMapper;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getClientOrders(String username) {
        if (username == null || username.isEmpty()) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }

        List<Order> orders = orderRepository.findByUsername(username);

        return orders.stream()
                .map(OrderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto createNewOrder(NewOrderRequest createNewOrderRequest) {
        log.info("Создание нового заказа");

        UUID shoppingCartId = createNewOrderRequest.getShoppingCart().getShoppingCartId();

        Map<UUID, Integer> products = createNewOrderRequest.getShoppingCart().getProducts().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));

        Order order = Order.builder()
                .shoppingCartId(shoppingCartId)
                .username(createNewOrderRequest.getUsername())
                .products(products)
                .state(OrderState.NEW)
                .build();

        Order savedOrder = orderRepository.save(order);

        BookedProductsDto bookedProduct = warehouseClient.assembleOrder(
                new AssemblyProductForOrderFromShoppingCartRequest(shoppingCartId, savedOrder.getOrderId()));

        savedOrder.setDeliveryWeight(bookedProduct.getDeliveryWeight());
        savedOrder.setDeliveryVolume(bookedProduct.getDeliveryVolume());
        savedOrder.setFragile(bookedProduct.getFragile());

        AddressDto fromAddressDto = warehouseClient.getAddress();
        Address toAddress = AddressMapper.mapFromRequest(createNewOrderRequest.getAddress());
        savedOrder.setFromAddress(AddressMapper.mapFromRequest(fromAddressDto));
        savedOrder.setToAddress(toAddress);

        DeliveryDto deliveryDto = DeliveryDto.builder()
                .deliveryId(UUID.randomUUID())
                .orderId(savedOrder.getOrderId())
                .fromAddress(fromAddressDto)
                .toAddress(createNewOrderRequest.getAddress())
                .status(DeliveryState.CREATED)
                .build();

        DeliveryDto createdDelivery = deliveryClient.planDelivery(deliveryDto);
        savedOrder.setDeliveryId(createdDelivery.getDeliveryId());

        BigDecimal productPrice = paymentClient.productCost(OrderMapper.mapToOrderDto(savedOrder));
        savedOrder.setProductPrice(productPrice);

        BigDecimal deliveryPrice = deliveryClient.deliveryCost(OrderMapper.mapToOrderDto(savedOrder));
        savedOrder.setDeliveryPrice(deliveryPrice);

        BigDecimal totalPrice = paymentClient.getTotalCost(OrderMapper.mapToOrderDto(savedOrder));
        savedOrder.setTotalPrice(totalPrice);

        orderRepository.save(savedOrder);

        log.info("Заказ создан: {}", savedOrder.getOrderId());

        return OrderMapper.mapToOrderDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto payment(UUID orderId) {
        log.info("Инициация оплаты для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        PaymentDto paymentDto = paymentClient.payment(OrderMapper.mapToOrderDto(order));
        order.setPaymentId(paymentDto.getPaymentId());
        order.setState(OrderState.PAID);

        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto paymentFailed(UUID orderId) {
        log.info("Обработка неудачной оплаты для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.PAYMENT_FAILED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto delivery(UUID orderId) {
        log.info("Инициация доставки для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.DELIVERED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto deliveryFailed(UUID orderId) {
        log.info("Обработка неудачной доставки для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.DELIVERY_FAILED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto completed(UUID orderId) {
        log.info("Завершение заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.COMPLETED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateTotalCost(UUID orderId) {
        log.info("Расчет общей стоимости для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        BigDecimal totalPrice = paymentClient.getTotalCost(OrderMapper.mapToOrderDto(order));
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateDeliveryCost(UUID orderId) {
        log.info("Расчет стоимости доставки для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        BigDecimal deliveryPrice = deliveryClient.deliveryCost(OrderMapper.mapToOrderDto(order));
        order.setDeliveryPrice(deliveryPrice);

        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto assembly(UUID orderId) {
        log.info("Обработка успешной сборки для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.ASSEMBLED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto assemblyFailed(UUID orderId) {
        log.info("Обработка неудачной сборки для заказа: {}", orderId);
        Order order = checkOrder(orderId);

        order.setState(OrderState.ASSEMBLY_FAILED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto productReturn(ProductReturnRequest productReturnRequest) {
        log.info("Обработка возврата для заказа: {}", productReturnRequest.getOrderId());
        Order order = checkOrder(productReturnRequest.getOrderId());

        order.setState(OrderState.PRODUCT_RETURNED);
        orderRepository.save(order);

        return OrderMapper.mapToOrderDto(order);
    }

    private Order checkOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException(String.format("Заказ id = %s не найден", orderId))
        );
    }
}
