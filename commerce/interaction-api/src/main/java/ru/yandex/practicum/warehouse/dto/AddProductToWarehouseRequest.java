package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToWarehouseRequest {

    @UUID
    @NotNull
    String productId;

    @NotNull
    @Min(value = 1)
    Integer quantity;
}
