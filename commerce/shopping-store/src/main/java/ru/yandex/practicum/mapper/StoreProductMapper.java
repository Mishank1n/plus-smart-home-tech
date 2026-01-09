package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.StoreProduct;
import ru.yandex.practicum.store.dto.ProductDto;

public class StoreProductMapper {

    public static StoreProduct toStoreProduct(ProductDto product) {
        return StoreProduct.builder()
                .productCategory(product.getProductCategory())
                .productName(product.getProductName())
                .productState(product.getProductState())
                .price(product.getPrice())
                .description(product.getDescription())
                .imageSrc(product.getImageSrc())
                .quantityState(product.getQuantityState())
                .build();
    }

    public static ProductDto toDto(StoreProduct product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productState(product.getProductState())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantityState(product.getQuantityState())
                .productCategory(product.getProductCategory())
                .imageSrc(product.getImageSrc())
                .build();
    }
}
