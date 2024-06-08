package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, int ownerId) {
        return ItemMapper.itemToDto(itemRepository.addNewItem(ItemMapper.dtoToItem(itemDto), ownerId));
    }

    @Override
    public ItemDto updateItem(int itemId, int ownerId, ItemDto itemDto) {
        return ItemMapper.itemToDto(itemRepository.updateItem(itemId, ownerId, ItemMapper.dtoToItem(itemDto)));
    }

    @Override
    public ItemDto getItem(int itemId) {
        return ItemMapper.itemToDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> getAllItems(int ownerId) {
        return itemRepository.getAllItems(ownerId).stream().map(ItemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        return itemRepository.searchForItems(query).stream().map(ItemMapper::itemToDto).collect(Collectors.toList());
    }
}
