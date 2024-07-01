package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookingsAndComments;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto addNewItem(ItemDto itemDto, Long ownerId);

    ItemDto updateItem(Long itemId, Long ownerId, ItemDto itemDto);

    ItemDtoWithBookingsAndComments getItem(Long itemId, Long userId);

    List<ItemDtoWithBookingsAndComments> getAllItems(Long ownerId);

    List<ItemDto> searchForItems(String query);

    CommentDto addComment(Long itemId, Long authorId, CommentDto commentDto);

    Item findItem(Long itemId);
}
