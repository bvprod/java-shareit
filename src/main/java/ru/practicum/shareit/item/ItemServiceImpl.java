package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemDto addNewItem(Item item, int ownerId) {
        return itemRepository.addNewItem(item, ownerId);
    }

    @Override
    public ItemDto updateItem(int itemId, int ownerId, ItemDto itemDto) {
        return itemRepository.updateItem(itemId, ownerId, itemDto);
    }

    @Override
    public ItemDto getItem(int itemId) {
        return itemRepository.getItem(itemId);
    }

    @Override
    public List<ItemDto> getAllItems(int ownerId) {
        return itemRepository.getAllItems(ownerId);
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        return itemRepository.searchForItems(query);
    }
}
