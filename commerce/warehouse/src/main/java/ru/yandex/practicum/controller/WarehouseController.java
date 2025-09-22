package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.warehouse.AddressDto;
import ru.yandex.practicum.dto.warehouse.AssemblyProductForOrderFromShoppingCartRequest;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.ShippedToDeliveryRequest;
import ru.yandex.practicum.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.service.WarehouseService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PutMapping
    public void addProduct(@RequestBody @Valid NewProductInWarehouseRequest newProductInWarehouseRequestDto) {
        warehouseService.addProduct(newProductInWarehouseRequestDto);
    }

    @PostMapping("/check")
    public BookedProductsDto checkProductQuantity(@RequestBody @Valid ShoppingCartDto shoppingCartDto) {
        return warehouseService.checkProductQuantity(shoppingCartDto);
    }

    @PostMapping("/add")
    public void updateProductQuantity(@RequestBody @Valid AddProductToWarehouseRequest addProductToWarehouseRequestDto) {
        warehouseService.updateProductQuantity(addProductToWarehouseRequestDto);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }

    @PostMapping("/orders/{orderId}/ship/{deliveryId}")
    public void shipOrder(@PathVariable UUID orderId, @PathVariable UUID deliveryId) {
        warehouseService.shipOrder(orderId, deliveryId);
    }

    @PostMapping("/orders/{orderId}/return")
    public void returnProducts(@PathVariable UUID orderId,
                               @RequestBody @Valid BookedProductsDto bookedProducts) {
        warehouseService.returnProducts(orderId, bookedProducts);
    }

    @PostMapping("/orders/assemble")
    public BookedProductsDto assembleOrder(@RequestBody @Valid AssemblyProductForOrderFromShoppingCartRequest request) {
        return warehouseService.assembleOrder(request);
    }

    @PostMapping("/orders/shipped")
    public void shippedToDelivery(@RequestBody ShippedToDeliveryRequest request) {
        warehouseService.shippedToDelivery(request);
    }
}
