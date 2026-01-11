package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.cart.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.cart.dto.ShoppingCartDto;
import ru.yandex.practicum.cart.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.cart.exception.NotFoundCartException;
import ru.yandex.practicum.mapper.ShoppingCartItemMapper;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.model.ShoppingCartItem;
import ru.yandex.practicum.repository.ShoppingCartItemRepository;
import ru.yandex.practicum.repository.ShoppingCartRepository;
import ru.yandex.practicum.warehouse.client.WarehouseClient;
import ru.yandex.practicum.warehouse.dto.BookedProductsDto;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImp implements ShoppingCartService {

    private final String cartNotFoundErrorMessage = "Корзина пользователя %s не найдена";
    private final String cartItemNotFoundErrorMessage = "В корзине пользователя %s нет товара с id = %s";

    @Autowired
    private final ShoppingCartRepository cartRepository;

    @Autowired
    private final ShoppingCartItemRepository cartItemRepository;

    private final WarehouseClient warehouseClient;

    @Override
    public ShoppingCartDto getCart(String userName) {
        return ShoppingCartMapper.toDto(cartRepository.findByOwner(userName).orElseThrow(() ->
                new NotFoundCartException(String.format(cartNotFoundErrorMessage, userName))
        ));
    }

    @Override
    public ShoppingCartDto put(String userName, Map<String, Integer> products) {
        if (cartRepository.findByOwner(userName).isPresent()) {
            String id = cartRepository.findByOwner(userName).get().getShoppingCartId();
            cartRepository.deleteById(id);
        }
        ShoppingCart shoppingCart = cartRepository.save(ShoppingCartMapper.toShoppingCarCreate(userName));
        shoppingCart.setShoppingCartItems(cartItemRepository.saveAll(ShoppingCartItemMapper.toList(shoppingCart, products)));
        ShoppingCartDto shoppingCartDto = ShoppingCartMapper.toDto(shoppingCart);
        BookedProductsDto bookedProductsDto = warehouseClient.checkQuantity(shoppingCartDto);
        return shoppingCartDto;
    }

    @Override
    public void delete(String userName) {
        if (cartRepository.findByOwner(userName).isPresent()) {
            String id = cartRepository.findByOwner(userName).get().getShoppingCartId();
            cartRepository.deleteById(id);
        }
    }

    @Override
    public ShoppingCartDto removeItems(String userName, List<String> productIds) {
        ShoppingCart shoppingCart = cartRepository.findByOwner(userName).orElseThrow(() ->
                new NotFoundCartException(String.format(cartNotFoundErrorMessage, userName)));
        productIds.forEach(removeId -> {
            if (shoppingCart.getShoppingCartItems().stream().map(ShoppingCartItem::getProductId)
                    .noneMatch(productId -> productId.equals(removeId))) {
                throw new NoProductsInShoppingCartException(String.format(cartItemNotFoundErrorMessage, userName, removeId));
            }
            shoppingCart.getShoppingCartItems().remove(shoppingCart.getShoppingCartItems().stream().filter(shoppingCartItem -> shoppingCartItem.getProductId().equals(removeId)).findFirst().get());
        });
        return ShoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto changeQuantityForItem(String userName, ChangeProductQuantityRequest request) {
        ShoppingCart shoppingCart = cartRepository.findByOwner(userName).orElseThrow(() ->
                new NotFoundCartException(String.format(cartNotFoundErrorMessage, userName)));
        ShoppingCartItem cartItem = shoppingCart.getShoppingCartItems().stream()
                .filter(shoppingCartItem -> shoppingCartItem.getProductId().equals(request.getProductId()))
                .findFirst()
                .orElseThrow(() -> new NoProductsInShoppingCartException(String.format(cartItemNotFoundErrorMessage, userName, request.getProductId())));
        if (request.getNewQuantity() == 0) {
            shoppingCart.getShoppingCartItems().remove(cartItem);
        } else {
            cartItem.setQuantity(request.getNewQuantity());
            cartItemRepository.save(cartItem);
        }
        return ShoppingCartMapper.toDto(shoppingCart);
    }
}
