package ru.yandex.practicum.repository.dimension;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Dimension;

public interface DimensionRepository extends JpaRepository<Dimension, Long> {
}
