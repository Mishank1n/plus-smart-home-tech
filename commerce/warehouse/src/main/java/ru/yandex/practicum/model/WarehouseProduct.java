package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse_products", schema = "public")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseProduct {

    @Id
    @Column(name = "product_id", nullable = false)
    String productId;

    @Column(name = "fragile", nullable = false)
    Boolean fragile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_id", referencedColumnName = "id", nullable = false)
    Dimension dimension;

    @Column(name = "weight", nullable = false)
    Double weight;

    @Column(name = "quantity", nullable = false)
    Integer quantity;
}
