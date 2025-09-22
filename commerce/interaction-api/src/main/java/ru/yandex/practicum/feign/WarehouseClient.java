package ru.yandex.practicum.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductForOrderFromShoppingCartRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.NewProductInWarehouseRequest;

import java.util.UUID;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PutMapping
    void addProduct(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequest);

    @PostMapping("/check")
    BookedProductsDto checkProductQuantity(@RequestBody @Valid ShoppingCartDto shoppingCartDto);

    @PostMapping("/add")
    void updateProductQuantity(@RequestBody AddProductToWarehouseRequest addProductToWarehouseRequest);

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/orders/{orderId}/ship/{deliveryId}")
    void shipOrder(@PathVariable UUID orderId, @PathVariable UUID deliveryId);

    @PostMapping("/orders/{orderId}/return")
    void returnProducts(@PathVariable UUID orderId, @RequestBody @Valid BookedProductsDto bookedProducts);

    @PostMapping("/orders/assemble")
    BookedProductsDto assembleOrder(@RequestBody @Valid AssemblyProductForOrderFromShoppingCartRequest request);

    @PostMapping("/shipped")
    void shippedToDelivery(@RequestBody ShippedToDeliveryRequest request);
}
