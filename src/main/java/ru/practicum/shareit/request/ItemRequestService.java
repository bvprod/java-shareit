package ru.practicum.shareit.request;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto addRequest(ItemRequestDto itemRequestDto, Long userId);

    List<ItemRequestDtoWithItems> getUserRequests(Long userId);

    List<ItemRequestDto> getAllRequests(Long userId, int from, int size);

    ItemRequestDtoWithItems getRequest(Long userId, Long requestId);

    ItemRequest findItemRequest(Long requestId);
}
