package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "itemId", expression = "java(entity.getItem().getId())")
    @Mapping(target = "bookerId", expression = "java(entity.getBooker().getId())")
    BookingDto entityToDto(Booking entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "booker", source = "user")
    Booking dtoToEntity(BookingDto dto, Item item, User user);

    BookingDtoOutput entityToOutputDto(Booking entity);

    @Mapping(target = "bookerId", expression = "java(entity.getBooker().getId())")
    BookingDtoShort entityToShortDto(Booking entity);
}
