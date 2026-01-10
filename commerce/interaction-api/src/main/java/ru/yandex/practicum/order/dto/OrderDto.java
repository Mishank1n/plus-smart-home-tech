package ru.yandex.practicum.order.dto;

import jakarta.validation.constraints.NotEmpty;
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
    String orderId;

    @UUID
    String shoppingCartId;

    @NotEmpty
    Map<String, Integer> products;

    @UUID
    String paymentId;

    @UUID
    String deliveryId;

    OrderState state;

    Double deliveryWeight;

    Double deliveryVolume;

    Boolean fragile;

    BigDecimal totalPrice;

    BigDecimal deliveryPrice;

    BigDecimal productPrice;

    String username;
}
