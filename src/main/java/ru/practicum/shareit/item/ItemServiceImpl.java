package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto addNewItem(ItemDto itemDto, Long ownerId) {
        Item item = ItemMapper.dtoToItem(itemDto,
                userRepository
                        .findById(ownerId)
                        .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует")));
        return ItemMapper.itemToDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(Long itemId, Long ownerId, ItemDto itemDto) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new WrongOwnerException("Данный пользователь не является владельцем этой вещи");
        }
        Item newItem = ItemMapper.dtoToItem(itemDto, item.getOwner());
        newItem.setId(itemId);
        return ItemMapper.itemToDto(itemRepository.save(newItem));
    }

    @Override
    public ItemDto getItem(Long itemId) {
        return ItemMapper.itemToDto(itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует")));
    }

    @Override
    public List<ItemDto> getAllItems(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId).stream().map(ItemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        return itemRepository.search(query).stream().map(ItemMapper::itemToDto).collect(Collectors.toList());
    }
}
