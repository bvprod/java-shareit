package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "itemId", expression = "java(entity.getItem().getId())")
    @Mapping(target = "bookerId", expression = "java(entity.getBooker().getId())")
    BookingDto entityToDto(Booking entity);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "booker", source = "user")
    Booking dtoToEntity(BookingDto dto, Item item, User user);

    @Mapping(target = "item", expression = "java(itemMapper.entityToDto(entity.getItem()))")
    @Mapping(target = "booker", expression = "java(userMapper.entityToDto(entity.getBooker()))")
    BookingDtoOutput entityToOutputDto(Booking entity);

    @Mapping(target = "bookerId", expression = "java(entity.getBooker().getId())")
    BookingDtoShort entityToShortDto(Booking entity);
}
