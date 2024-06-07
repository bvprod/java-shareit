package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    ItemDto addNewItem(Item item, int ownerId);

    ItemDto updateItem(int itemId, int ownerId, ItemDto item);

    ItemDto getItem(int itemId);

    List<ItemDto> getAllItems(int ownerId);

    List<ItemDto> searchForItems(String query);
}
