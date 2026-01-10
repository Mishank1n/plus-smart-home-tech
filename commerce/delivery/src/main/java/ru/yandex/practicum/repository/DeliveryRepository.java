package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
}
