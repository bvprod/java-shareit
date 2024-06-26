package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ItemMapper {
    public static ItemDto itemToDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequest());
    }

    public static Item dtoToItem(ItemDto itemDto, User user) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                user,
                itemDto.getRequest());
    }
}
