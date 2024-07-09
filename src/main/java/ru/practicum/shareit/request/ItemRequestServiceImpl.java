package ru.practicum.shareit.request;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserService userService;

    private final ItemRequestMapper itemRequestMapper = Mappers.getMapper(ItemRequestMapper.class);

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, UserService userService) {
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
    }

    @Override
    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, Long userId) {
        User user = userService.findUser(userId);
        ItemRequest itemRequest = itemRequestMapper.dtoToEntity(itemRequestDto, user);
        return itemRequestMapper.entityToDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public List<ItemRequestDtoWithItems> getUserRequests(Long userId) {
        userService.getUser(userId);
        return itemRequestRepository
                .findByRequesterIdOrderByCreatedDesc(userId)
                .stream().map(itemRequestMapper::entityToDtoWithItems).toList();
    }

    @Override
    public List<ItemRequestDto> getAllRequests(Long userId, int from, int size) {
        userService.getUser(userId);
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return itemRequestRepository.findByRequesterIdNotOrderByCreatedDesc(userId, page)
                .stream()
                .map(itemRequestMapper::entityToDto)
                .toList();
    }

    @Override
    public ItemRequestDtoWithItems getRequest(Long userId, Long requestId) {
        userService.getUser(userId);
        return itemRequestMapper
                .entityToDtoWithItems(itemRequestRepository
                        .findById(requestId)
                        .orElseThrow(() -> new ObjectDoesNotExistException("Данного запроса не существует")));
    }

    @Override
    public ItemRequest findItemRequest(Long requestId) {
        return itemRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Данного запроса не существует"));
    }
}