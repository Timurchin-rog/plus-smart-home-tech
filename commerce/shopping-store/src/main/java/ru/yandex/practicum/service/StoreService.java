package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.dto.store.ProductDto;
import ru.yandex.practicum.enums.store.QuantityState;
import ru.yandex.practicum.enums.store.ProductCategory;

import java.util.UUID;

public interface StoreService {

    Page<ProductDto> getProducts(ProductCategory category, Pageable pageable);

    ProductDto getProductById(UUID productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean removeProduct(UUID productId);

    boolean updateQuantityState(UUID productId, QuantityState quantityState);
}
