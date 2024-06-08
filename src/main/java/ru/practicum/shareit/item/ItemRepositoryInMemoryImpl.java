package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryInMemoryImpl implements ItemRepository {

    private final UserService userService;
    private final Map<Integer, Item> items = new HashMap<>();
    private int id = 1;

    @Override
    public Item addNewItem(Item item, int ownerId) {
        userService.getUser(ownerId);
        item.setId(id++);
        item.setOwnerId(ownerId);
        items.put(item.getId(), item);
        log.info(String.format("Добавлена новая вещь %s, id: %d", item.getName(), item.getId()));
        return items.get(item.getId());
    }

    @Override
    public Item updateItem(int itemId, int ownerId, Item newitem) {
        userService.getUser(ownerId);
        Item item = getItem(itemId);
        if (item.getOwnerId() != ownerId) {
            throw new WrongOwnerException("Данная вещь не принадлежит указанному пользователю");
        }
        if (newitem.getName() != null && !newitem.getName().isBlank()) {
            item.setName(newitem.getName());
        }
        if (newitem.getDescription() != null && !newitem.getDescription().isBlank()) {
            item.setDescription(newitem.getDescription());
        }
        if (newitem.getAvailable() != null) {
            item.setAvailable(newitem.getAvailable());
        }
        items.put(itemId, item);
        log.info("Обновлена информация о вещи, id=" + itemId);
        return item;
    }

    @Override
    public Item getItem(int itemId) {
        if (!items.containsKey(itemId)) {
            throw new ObjectDoesNotExistException("Вещи с данным id не существует");
        }
        log.info("Запрос информации о вещи, id=" + itemId);
        return items.get(itemId);
    }

    @Override
    public List<Item> getAllItems(int ownerId) {
        log.info("Запрос информации о всех вещах пользователя с id=" + ownerId);
        return items.values().stream()
                .filter(item -> item.getOwnerId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> searchForItems(String query) {
        log.info("Поиск вещи по запросу: " + query);
        String queryLowerCase = query.toLowerCase();
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(queryLowerCase) ||
                        item.getDescription().toLowerCase().contains(queryLowerCase))
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }
}
