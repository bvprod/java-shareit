package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addNewItem(ItemDto itemDto, Long ownerId);

    ItemDto updateItem(Long itemId, Long ownerId, ItemDto itemDto);

    ItemDto getItem(Long itemId);

    List<ItemDto> getAllItems(Long ownerId);

    List<ItemDto> searchForItems(String query);
}
