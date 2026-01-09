package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;

public class WarehouseProductMapper {

    public static WarehouseProduct toWarehouseProduct(NewProductInWarehouseRequest request) {
        return WarehouseProduct.builder()
                .productId(request.getProductId())
                .fragile(request.getFragile())
                .weight(request.getWeight())
                .build();
    }
}
