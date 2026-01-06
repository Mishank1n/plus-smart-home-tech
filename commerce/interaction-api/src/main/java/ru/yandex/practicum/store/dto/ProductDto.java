package ru.yandex.practicum.store.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

    @UUID
    String productId;

    @NotBlank
    String productName;

    @NotBlank
    String description;

    String imageSrc;

    @NotNull
    QuantityState quantityState;

    @NotNull
    ProductState productState;

    @NotNull
    ProductCategory productCategory;

    @NotNull
    @Min(value = 1)
    BigDecimal price;
}
