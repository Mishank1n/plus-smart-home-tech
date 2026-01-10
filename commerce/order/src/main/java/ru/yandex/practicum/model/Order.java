package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.order.dto.OrderState;

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "order", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", nullable = false)
    String orderId;

    @Column(name = "shopping_cart_id", nullable = false)
    String shoppingCartId;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<String, Integer> products;

    @Column(name = "payment_id")
    String paymentId;

    @Column(name = "delivery_id")
    String deliveryId;

    @Column(name = "state")
    OrderState state;

    @Column(name = "delivery_weight")
    Double deliveryWeight;

    @Column(name = "delivery_volume")
    Double deliveryVolume;

    @Column(name = "fragile")
    Boolean fragile;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "delivery_price")
    BigDecimal deliveryPrice;

    @Column(name = "product_price")
    BigDecimal productPrice;

    @Column(name = "username", nullable = false)
    String username;
}
