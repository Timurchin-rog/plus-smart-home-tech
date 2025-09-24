package ru.yandex.practicum.feign;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.store.ProductDto;
import ru.yandex.practicum.enums.store.ProductCategory;
import ru.yandex.practicum.enums.store.QuantityState;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store")
public interface ShoppingStoreClient {

    @GetMapping
    Page<ProductDto> getProducts(@RequestParam ProductCategory category, Pageable pageable);

    @PutMapping
    ProductDto createProduct(@RequestBody @Valid ProductDto newProductDto);

    @PostMapping
    ProductDto updateProduct(@RequestBody ProductDto updateProductDto);

    @PostMapping("/removeProductFromStore")
    Boolean removeProduct(@RequestBody @org.hibernate.validator.constraints.UUID UUID productId);

    @PostMapping("/quantityState")
    Boolean updateQuantityState(@RequestParam @org.hibernate.validator.constraints.UUID UUID productId,
                                @RequestParam @NotNull QuantityState quantityState);

    @GetMapping("/{product-id}")
    ProductDto getProductById(@PathVariable(name = "product-id") @org.hibernate.validator.constraints.UUID UUID productId);

    @PostMapping("/products/ids")
    Map<UUID, ProductDto> findAllByIds(@RequestBody @NotEmpty Set<UUID> ids);
}
