package ru.practicum.shareit.item;

import org.mapstruct.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateItemFromDto(ItemDto itemDto, @MappingTarget Item entity);

    @Mapping(target = "ownerId", expression = "java(entity.getOwner().getId())")
    ItemDto entityToDto(Item entity);

    @Mapping(target = "owner", source = "user")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "name", source = "dto.name")
    Item dtoToEntity(ItemDto dto, User user);

    @Mapping(target = "authorId", expression = "java(entity.getAuthor().getId())")
    @Mapping(target = "itemId", expression = "java(entity.getItem().getId())")
    @Mapping(target = "authorName", expression = "java(entity.getAuthor().getName())")
    CommentDto commentEntityToDto(Comment entity);

    @Mapping(target = "author", source = "author")
    @Mapping(target = "item", source = "item")
    @Mapping(target = "id", source = "dto.id")
    Comment commentDtoToEntity(CommentDto dto, User author, Item item);
}

