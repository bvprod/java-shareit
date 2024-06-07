package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryInMemoryImpl implements ItemRepository {

    private final UserService userService;
    private final Map<Integer, Item> items = new HashMap<>();
    private int id = 1;

    @Override
    public ItemDto addNewItem(Item item, int ownerId) {
        if (userService.getUser(ownerId) == null) {
            throw new ObjectDoesNotExistException("Вещь с данным id не существует");
        }
        item.setId(id++);
        item.setOwnerId(ownerId);
        items.put(item.getId(), item);
        return ItemMapper.itemToDto(items.get(item.getId()));
    }

    @Override
    public ItemDto updateItem(int itemId, int ownerId, ItemDto itemDto) {
        if (userService.getUser(ownerId) == null) {
            throw new ObjectDoesNotExistException("Пользователь с данным id не существует");
        }
        Item item = ItemMapper.dtoToItem(getItem(itemId));
        if (item.getOwnerId() != ownerId) {
            throw new WrongOwnerException("Данная вещь не принадлежит указанному пользователю");
        }
        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        items.put(itemId, item);
        return ItemMapper.itemToDto(item);
    }

    @Override
    public ItemDto getItem(int itemId) {
        if (!items.containsKey(itemId)) {
            throw new ObjectDoesNotExistException("Вещи с данным id не существует");
        }
        return ItemMapper.itemToDto(items.get(itemId));
    }

    @Override
    public List<ItemDto> getAllItems(int ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        String queryLowerCase = query.toLowerCase();
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(queryLowerCase) ||
                        item.getDescription().toLowerCase().contains(queryLowerCase))
                .filter(Item::getAvailable)
                .map(ItemMapper::itemToDto).collect(Collectors.toList());
    }
}
