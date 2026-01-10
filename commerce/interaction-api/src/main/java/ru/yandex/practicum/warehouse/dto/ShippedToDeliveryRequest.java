package ru.yandex.practicum.warehouse.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippedToDeliveryRequest {

    @NotBlank
    @UUID
    String orderId;

    @NotBlank
    @UUID
    String deliveryId;
}
