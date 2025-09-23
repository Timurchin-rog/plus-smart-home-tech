package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.dto.cart.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface CartService {

    ShoppingCartDto getShoppingCart(String username);

    ShoppingCartDto addProduct(String username, Map<UUID, Long> request);

    void deactivateCart(String username);

    ShoppingCartDto removeProduct(String username, Set<UUID> productsId);

    ShoppingCartDto updateProductQuantity(String username, ChangeProductQuantityRequest requestDto);

}