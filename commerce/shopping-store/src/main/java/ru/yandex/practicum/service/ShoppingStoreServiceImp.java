package ru.yandex.practicum.service;

import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.mapper.StoreProductMapper;
import ru.yandex.practicum.model.StoreProduct;
import ru.yandex.practicum.repository.StoreProductRepository;
import ru.yandex.practicum.store.dto.ProductCategory;
import ru.yandex.practicum.store.dto.ProductDto;
import ru.yandex.practicum.store.dto.ProductState;
import ru.yandex.practicum.store.dto.QuantityState;
import ru.yandex.practicum.store.exception.ProductNotFoundException;

import java.util.Set;

@AllArgsConstructor
@Service
public class ShoppingStoreServiceImp implements ShoppingStoreService {

    @Autowired
    private final StoreProductRepository repository;

    private static final Set<String> ALLOWED_SORT_PROPERTIES = Set.of(
            "productName", "price", "description"
    );

    @Override
    public Page<ProductDto> getByCategory(ProductCategory category, Pageable pageable) {
        Page<StoreProduct> products = repository.findAllByProductCategory(category, pageable);
        return products.map(StoreProductMapper::toDto);
    }

    @Override
    public ProductDto put(ProductDto productDto) {
        StoreProduct product = StoreProductMapper.toStoreProduct(productDto);
        return StoreProductMapper.toDto(repository.save(product));
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        if (productDto.getProductId().isBlank()) {
            throw new ValidationException("Id продукта не может быть пустым");
        }
        StoreProduct product = repository.findById(productDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(String.format("Не найден продукт с id = %s", productDto.getProductId())));
        return StoreProductMapper.toDto(repository.save(StoreProductMapper.toStoreProduct(productDto)));
    }

    @Override
    public ProductDto delete(String productId) {
        StoreProduct product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Не найден продукт с id = %s", productId)));
        product.setProductState(ProductState.DEACTIVATE);
        repository.save(product);
        return StoreProductMapper.toDto(product);

    }

    @Override
    public ProductDto changeProductQuantityStatus(String productId, QuantityState quantityState) {
        StoreProduct product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Не найден продукт с id = %s", productId)));
        product.setQuantityState(quantityState);
        repository.save(product);
        return StoreProductMapper.toDto(product);
    }

    @Override
    public ProductDto get(String productId) {
        return StoreProductMapper.toDto(repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Не найден продукт с id = %s", productId))));
    }
}
