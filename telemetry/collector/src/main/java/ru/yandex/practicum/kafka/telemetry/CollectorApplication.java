package ru.yandex.practicum.kafka.telemetry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// Явное сканирование для надежности
@ComponentScan(basePackages = {"ru.yandex.practicum.kafka.telemetry"})
public class CollectorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectorApplication.class, args);
    }
}