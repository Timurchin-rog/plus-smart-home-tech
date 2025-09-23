package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductForOrderFromShoppingCartRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.dto.warehouse.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.NewProductInWarehouseRequest;

import java.util.UUID;

public interface WarehouseService {

    void addProduct(NewProductInWarehouseRequest newProductInWarehouseRequest);

    BookedProductsDto checkProductQuantity(ShoppingCartDto shoppingCartDto);

    void updateProductQuantity(AddProductToWarehouseRequest addProductToWarehouseRequest);

    AddressDto getAddress();

    void shipOrder(UUID orderId, UUID deliveryId);

    void returnProducts(UUID orderId, BookedProductsDto bookedProducts);

    BookedProductsDto assembleOrder(AssemblyProductForOrderFromShoppingCartRequest request);

    void shippedToDelivery(ShippedToDeliveryRequest request);
}
