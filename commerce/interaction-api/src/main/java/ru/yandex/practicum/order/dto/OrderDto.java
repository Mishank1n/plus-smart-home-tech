package ru.yandex.practicum.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    @UUID
    @NotBlank
    String orderId;

    @UUID
    @NotBlank
    String shoppingCartId;

    @NotEmpty
    Map<String, Integer> products;

    String paymentId;

    String deliveryId;

    @NotNull
    OrderState state;

    Double deliveryWeight;

    Double deliveryVolume;

    Boolean fragile;

    BigDecimal totalPrice;

    BigDecimal deliveryPrice;

    BigDecimal productPrice;

    @NotBlank
    String username;
}