package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.delivery.dto.DeliveryState;

@Entity
@Table(name = "delivery_requests", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_id")
    String deliveryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_address_id", referencedColumnName = "address_id", nullable = false)
    Address fromAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_address_id", referencedColumnName = "address_id", nullable = false)
    Address toAddress;

    @NotBlank
    @Column(name = "order_id", nullable = false)
    String orderId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "deliveryState", nullable = false)
    DeliveryState deliveryState;
}
