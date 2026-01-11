package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Order;


public interface OrderRepository extends JpaRepository<Order, String> {

    Page<Order> findAllByUsername(String username, Pageable pageable);
}