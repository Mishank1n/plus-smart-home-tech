package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DimensionDto {

    @Min(value = 1)
    @NotNull
    Double width;

    @Min(value = 1)
    @NotNull
    Double height;

    @Min(value = 1)
    @NotNull
    Double depth;
}
