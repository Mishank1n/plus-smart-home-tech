package ru.yandex.practicum.mapper;

import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.warehouse.dto.AddressDto;

public class AddressMapper {

    public static AddressDto toDto(Address address) {
        return AddressDto.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }

    public static Address toModel(AddressDto address) {
        return Address.builder()
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .house(address.getHouse())
                .flat(address.getFlat())
                .build();
    }
}
