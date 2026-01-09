package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.Dimension;
import ru.yandex.practicum.warehouse.dto.DimensionDto;

public class DimensionMapper {

    public static Dimension toDimension(DimensionDto dimensionDto) {
        return Dimension.builder()
                .width(dimensionDto.getWidth())
                .depth(dimensionDto.getDepth())
                .height(dimensionDto.getHeight())
                .build();
    }
}
