package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.store.dto.ProductCategory;
import ru.yandex.practicum.store.dto.ProductDto;
import ru.yandex.practicum.store.dto.QuantityState;
import ru.yandex.practicum.store.dto.SetProductQuantityStateRequest;

public interface ShoppingStoreService {

    Page<ProductDto> getByCategory(ProductCategory category, Pageable pageable);

    ProductDto put(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    ProductDto delete(String productId);

    ProductDto changeProductQuantityStatus(SetProductQuantityStateRequest request);

    ProductDto get(String productId);
}
