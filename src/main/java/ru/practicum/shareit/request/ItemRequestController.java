package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addRequest(@Valid @RequestBody ItemRequestDto itemRequestDto,
                                     @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.addRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDtoWithItems> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getUserRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllRequests(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @RequestParam(value = "from", defaultValue = "0")
                                               @Min(value = 0, message = "Значение параметра from должно быть больше 0")
                                               int from,
                                               @RequestParam(value = "size", defaultValue = "10")
                                               @Min(value = 1, message = "Значение параметра size не может быть меньше 1")
                                               int size) {
        return itemRequestService.getAllRequests(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDtoWithItems getRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @PathVariable(value = "requestId") Long requestId) {
        return itemRequestService.getRequest(userId, requestId);
    }
}
