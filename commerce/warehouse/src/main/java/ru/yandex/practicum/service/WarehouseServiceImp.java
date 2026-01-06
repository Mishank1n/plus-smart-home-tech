package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.mapper.DimensionMapper;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.model.Dimension;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repository.dimension.DimensionRepository;
import ru.yandex.practicum.repository.product.WarehouseProductRepository;
import ru.yandex.practicum.warehouse.dto.AddProductToWarehouseRequest;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequest;
import ru.yandex.practicum.warehouse.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.warehouse.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.warehouse.exception.SpecifiedProductAlreadyInWarehouseException;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

@Service
@AllArgsConstructor
public class WarehouseServiceImp implements WarehouseService {

    @Autowired
    private final WarehouseProductRepository repository;

    @Autowired
    private final DimensionRepository dimensionRepository;

    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};

    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, ADDRESSES.length)];


    @Override
    public void put(NewProductInWarehouseRequest request) {
        if (repository.findById(request.getProductId()).isPresent()) {
            throw new SpecifiedProductAlreadyInWarehouseException(String.format("Продукт с id = %s уже имеется на складе", request.getProductId()));
        }
        Dimension dimension = dimensionRepository.save(DimensionMapper.toDimension(request.getDimension()));
        WarehouseProduct warehouseProduct = WarehouseProductMapper.toWarehouseProduct(request);
        warehouseProduct.setDimension(dimension);
        warehouseProduct.setQuantity(0);
        repository.save(warehouseProduct);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .city(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .country(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .build();
    }

    @Override
    public void addQuantity(AddProductToWarehouseRequest addRequest) {
        WarehouseProduct warehouseProduct = repository.findById(addRequest.getProductId()).orElseThrow(() -> new NoSpecifiedProductInWarehouseException(String.format("Нет информации о товаре с id = %s", addRequest.getProductId())));
        warehouseProduct.setQuantity(warehouseProduct.getQuantity() + addRequest.getQuantity());
        repository.save(warehouseProduct);
    }

    @Override
    public BookedProductsDto checkQuantity(ShoppingCartDto cartDto) {
        Double deliveryWeight = 0.0;
        Double deliveryVolume = 0.0;
        Boolean fragile = false;
        for (Map.Entry<String, Integer> cartProduct : cartDto.getProducts().entrySet()) {
            WarehouseProduct product = repository.findById(cartProduct.getKey()).get();
            if (fragile != true && product.getFragile() == true) {
                fragile = true;
            }
            if (product.getQuantity() >= cartProduct.getValue()) {
                deliveryWeight += product.getWeight();
                deliveryVolume += getVolumeForProduct(product);
            } else {
                throw new ProductInShoppingCartLowQuantityInWarehouseException(String.format("Количество товара с id = %s равно %d, нельзя взять %d", product.getProductId(), product.getQuantity(), cartProduct.getValue()));
            }
        }
        return BookedProductsDto.builder().deliveryWeight(deliveryWeight).deliveryVolume(deliveryVolume).fragile(fragile).build();
    }

    private Double getVolumeForProduct(WarehouseProduct product) {
        Dimension dimension = product.getDimension();
        return dimension.getDepth() * dimension.getHeight() * dimension.getWidth();
    }
}
