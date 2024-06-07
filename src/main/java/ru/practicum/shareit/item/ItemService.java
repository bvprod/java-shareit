package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

interface ItemService {

    ItemDto addNewItem(Item itemDto, int ownerId);

    ItemDto updateItem(int itemId, int ownerId, ItemDto itemDto);

    ItemDto getItem(int itemId);

    List<ItemDto> getAllItems(int ownerId);

    List<ItemDto> searchForItems(String query);
}
