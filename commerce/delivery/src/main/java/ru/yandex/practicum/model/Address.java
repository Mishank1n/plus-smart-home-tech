package ru.yandex.practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "delivery_addresses", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    String addressId;

    @NotBlank
    @Column(name = "country", nullable = false)
    String country;

    @NotBlank
    @Column(name = "city", nullable = false)
    String city;

    @NotBlank
    @Column(name = "street", nullable = false)
    String street;

    @NotBlank
    @Column(name = "house", nullable = false)
    String house;

    @NotBlank
    @Column(name = "flat", nullable = false)
    String flat;
}