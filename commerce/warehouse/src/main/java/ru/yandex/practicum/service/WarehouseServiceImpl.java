package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.warehouse.AssemblyProductForOrderFromShoppingCartRequest;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.*;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    private static final String[] ADDRESSES = {"ADDRESS_1", "ADDRESS_2"};

    @Override
    public void addProduct(NewProductInWarehouseRequest requestDto) {
        UUID productId = requestDto.getProductId();

        if (warehouseRepository.existsById(productId)) {
            throw new IllegalStateException(
                    String.format("Товар с ID = %s уже заведен на склад", productId));
        }

        Warehouse product = WarehouseMapper.mapFromRequest(requestDto);
        warehouseRepository.save(product);
        log.info("Добавлен новый товар на склад: {}", productId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookedProductsDto checkProductQuantity(ShoppingCartDto cartDto) {
        double totalWeight = 0.0;
        double totalVolume = 0.0;
        boolean hasFragile = false;

        for (Map.Entry<UUID, Long> entry : cartDto.getProducts().entrySet()) {
            UUID productId = entry.getKey();
            long requestedQty = entry.getValue();

            Warehouse product = getProduct(productId);

            if (product.getQuantity() < requestedQty) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException(
                        String.format("Недостаточно товара на складе. ID: %s, запрошено: %d, доступно: %d",
                                productId, requestedQty, product.getQuantity()));
            }

            totalWeight += product.getWeight() * requestedQty;
            totalVolume += product.getDimensionDto().getWidth() *
                    product.getDimensionDto().getHeight() *
                    product.getDimensionDto().getDepth() * requestedQty;

            if (product.getFragile()) hasFragile = true;
        }

        return BookedProductsDto.builder()
                .deliveryWeight(totalWeight)
                .deliveryVolume(totalVolume)
                .fragile(hasFragile)
                .build();
    }

    @Override
    public void updateProductQuantity(AddProductToWarehouseRequest requestDto) {
        UUID productId = requestDto.getProductId();
        Warehouse product = getProduct(productId);
        product.setQuantity(product.getQuantity() + requestDto.getQuantity());
        warehouseRepository.save(product);
    }

    @Override
    public AddressDto getAddress() {
        String address = Address.CURRENT_ADDRESS;
        return AddressDto.builder()
                .country(address)
                .city(address)
                .street(address)
                .house(address)
                .flat(address)
                .build();
    }

    @Override
    public void shipOrder(UUID orderId, UUID deliveryId) {
        log.info("Заказ {} отправлен на доставку {}", orderId, deliveryId);
    }

    @Override
    public void returnProducts(UUID orderId, BookedProductsDto bookedProducts) {
        log.info("Обработан возврат заказа {}: товары возвращены на склад", orderId);
    }

    @Override
    public BookedProductsDto assembleOrder(AssemblyProductForOrderFromShoppingCartRequest request) {
        log.info("Собран заказ {}: товары зарезервированы на складе", request.getOrderId());
        return null;
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        log.info("Заказ {} передан в доставку {}", request.getOrderId(), request.getDeliveryId());
    }

    private Warehouse getProduct(UUID productId) {
        return warehouseRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(String.format("Продукт id = %s не найден на складе", productId))
        );
    }
}
