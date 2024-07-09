package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {
    @Mapping(target = "requesterId", expression = "java(entity.getRequester().getId())")
    ItemRequestDto entityToDto(ItemRequest entity);

    @Mapping(target = "requester", source = "user")
    @Mapping(target = "id", source = "dto.id")
    ItemRequest dtoToEntity(ItemRequestDto dto, User user);

    @Mapping(target = "requesterId", expression = "java(entity.getRequester().getId())")
    ItemRequestDtoWithItems entityToDtoWithItems(ItemRequest entity);
}
