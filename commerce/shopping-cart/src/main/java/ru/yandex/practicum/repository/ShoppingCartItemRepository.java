package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShoppingCartItem;


public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

}
