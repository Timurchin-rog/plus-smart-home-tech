package ru.yandex.practicum.dto.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.enums.store.ProductCategory;
import ru.yandex.practicum.enums.store.ProductState;
import ru.yandex.practicum.enums.store.QuantityState;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @org.hibernate.validator.constraints.UUID
    UUID productId;

    @NotBlank
    String productName;

    @NotBlank
    String description;

    @NotBlank
    String imageSrc;

    @NotNull
    QuantityState quantityState;

    @NotNull
    ProductState productState;

    @NotNull
    ProductCategory productCategory;

    @Positive
    BigDecimal price;
}
