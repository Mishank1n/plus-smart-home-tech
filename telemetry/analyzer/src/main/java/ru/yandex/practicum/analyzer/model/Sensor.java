package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "sensors", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor {

    @Id
    String id;

    @Column(name = "hub_id")
    String hubId;
}
