package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository {
    Item addNewItem(Item item, int ownerId);

    Item updateItem(int itemId, int ownerId, Item newItem);

    Item getItem(int itemId);

    List<Item> getAllItems(int ownerId);

    List<Item> searchForItems(String query);
}
