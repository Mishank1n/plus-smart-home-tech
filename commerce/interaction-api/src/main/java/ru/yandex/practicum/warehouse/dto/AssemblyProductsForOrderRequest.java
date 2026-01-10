package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssemblyProductsForOrderRequest {

    @NotEmpty
    Map<String, Integer> products;

    @NotBlank
    @UUID
    String orderId;
}
