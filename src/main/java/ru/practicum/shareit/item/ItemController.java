package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@Valid @RequestBody Item item,
                              @RequestHeader("X-Sharer-User-Id") int ownerId) {
        return itemService.addNewItem(item, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable(value = "itemId") int itemId,
                              @RequestHeader("X-Sharer-User-Id") int ownerId,
                              @Valid @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemId, ownerId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable(value = "itemId") int itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") int ownerId) {
        return itemService.getAllItems(ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchForItems(@RequestParam(value = "text") String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        return itemService.searchForItems(query);
    }


}
