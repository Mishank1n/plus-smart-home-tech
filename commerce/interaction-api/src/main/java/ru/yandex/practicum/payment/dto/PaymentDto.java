package ru.yandex.practicum.payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {

    @UUID
    String paymentId;

    @Min(value = 0)
    BigDecimal totalPayment;

    @Min(value = 0)
    BigDecimal deliveryTotal;
    ;

    @Min(value = 0)
    BigDecimal feeTotal;

    @Min(value = 0)
    BigDecimal productTotal;

    @NotNull
    PaymentState paymentState;

    @NotBlank
    String orderId;
}
