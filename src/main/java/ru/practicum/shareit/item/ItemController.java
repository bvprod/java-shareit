package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookingsAndComments;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemDto addNewItem(@Valid @RequestBody ItemDto item,
                              @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.addNewItem(item, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable(value = "itemId") long itemId,
                              @RequestHeader("X-Sharer-User-Id") long ownerId,
                              @RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemId, ownerId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWithBookingsAndComments getItem(@PathVariable(value = "itemId") long itemId,
                                                  @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping
    public List<ItemDtoWithBookingsAndComments> getAllItems(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getAllItems(ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchForItems(@RequestParam(value = "text") String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        return itemService.searchForItems(query);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable long itemId,
                                 @RequestHeader("X-Sharer-User-Id") long authorId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return itemService.addComment(itemId, authorId, commentDto);
    }


}
