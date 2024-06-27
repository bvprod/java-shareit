package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper mapper;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper mapper) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public ItemDto addNewItem(ItemDto itemDto, Long ownerId) {
        User owner = userRepository
                .findById(ownerId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        return mapper.entityToDto(itemRepository.save(mapper.dtoToEntity(itemDto, owner)));
    }

    @Override
    public ItemDto updateItem(Long itemId, Long ownerId, ItemDto itemDto) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new WrongOwnerException("Данный пользователь не является владельцем этой вещи");
        }
        mapper.updateItemFromDto(itemDto, item);
        return mapper.entityToDto(itemRepository.save(item));
    }

    @Override
    public ItemDto getItem(Long itemId) {
        return mapper.entityToDto(itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует")));
    }

    @Override
    public List<ItemDto> getAllItems(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        return itemRepository.search(query).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }
}
