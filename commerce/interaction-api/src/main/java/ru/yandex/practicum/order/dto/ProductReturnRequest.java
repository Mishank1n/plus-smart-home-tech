package ru.yandex.practicum.order.dto;

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
public class ProductReturnRequest {

    @UUID
    @NotBlank
    String orderId;

    @NotEmpty
    Map<String, Integer> products;
}
