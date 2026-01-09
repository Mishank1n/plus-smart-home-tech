package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.store.dto.ProductCategory;
import ru.yandex.practicum.store.dto.ProductState;
import ru.yandex.practicum.store.dto.QuantityState;

import java.math.BigDecimal;

@Entity
@Table(name = "shopping_store_products", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_product_id", nullable = false)
    String productId;

    @Column(name = "product_name", nullable = false)
    @NotBlank
    String productName;

    @Column(name = "description", nullable = false)
    @NotBlank
    String description;

    @Column(name = "image_src")
    String imageSrc;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "quantity_state", nullable = false)
    QuantityState quantityState;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_state", nullable = false)
    ProductState productState;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "product_category", nullable = false)
    ProductCategory productCategory;

    @Column(name = "price", nullable = false)
    BigDecimal price;
}