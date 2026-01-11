package ru.yandex.practicum.delivery.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;
import ru.yandex.practicum.warehouse.dto.AddressDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryDto {

    String deliveryId;

    @Valid
    @NotNull
    AddressDto fromAddress;

    @Valid
    @NotNull
    AddressDto toAddress;

    @NotBlank
    String orderId;

    @NotNull
    DeliveryState deliveryState;
}