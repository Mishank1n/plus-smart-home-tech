package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;
import ru.yandex.practicum.payment.dto.PaymentState;

import java.math.BigDecimal;

@Entity
@Table(name = "payment", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_id", nullable = false)
    String paymentId;

    @Column(name = "order_id", nullable = false)
    @UUID
    String orderId;

    @Column(name = "payment_total")
    BigDecimal totalPayment;

    @Column(name = "payment_delivery_total")
    BigDecimal deliveryTotal;

    @Column(name = "payment_product_total")
    BigDecimal productTotal;

    @Column(name = "payment_fee_total")
    BigDecimal feeTotal;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_state", nullable = false)
    PaymentState paymentState;
}
