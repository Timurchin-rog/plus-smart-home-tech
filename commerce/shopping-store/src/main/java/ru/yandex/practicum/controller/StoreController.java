package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.store.ProductDto;
import ru.yandex.practicum.enums.store.QuantityState;
import ru.yandex.practicum.enums.store.ProductCategory;
import ru.yandex.practicum.service.StoreService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/shopping-store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public Page<ProductDto> getProducts(@RequestParam ProductCategory category, Pageable pageable) {
        return storeService.getProducts(category, pageable);
    }

    @GetMapping("/{product-id}")
    public ProductDto getProductById(@PathVariable(name = "product-id") @org.hibernate.validator.constraints.UUID UUID productId) {
        return storeService.getProductById(productId);
    }

    @PutMapping
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return storeService.createProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        return storeService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProduct(@RequestBody @org.hibernate.validator.constraints.UUID UUID productId) {
        return storeService.removeProduct(productId);
    }

    @PostMapping("/quantityState")
    public Boolean updateQuantityState(@RequestParam @org.hibernate.validator.constraints.UUID UUID productId,
                                       @RequestParam @NotNull QuantityState quantityState) {
        return storeService.updateQuantityState(productId, quantityState);
    }

    @PostMapping("/products/ids")
    public Map<UUID, ProductDto> findAllByIds(@RequestBody @NotEmpty Set<UUID> ids) {
        return ids.stream()
                .collect(Collectors.toMap(
                        id -> id,
                        id -> ProductDto.builder().price(BigDecimal.ZERO).build()
                ));
    }
}
