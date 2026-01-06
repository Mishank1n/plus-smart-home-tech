package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Table(name = "shopping_carts", schema = "public")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shopping_cart_id", nullable = false)
    String shoppingCartId;

    @NotBlank
    @Column(name = "owner_id", nullable = false)
    String owner;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ShoppingCartItem> shoppingCartItems;
}
