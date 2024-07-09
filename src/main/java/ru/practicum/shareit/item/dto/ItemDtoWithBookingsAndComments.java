package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithBookingsAndComments {
    @JsonIgnore
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank(message = "Описание должно быть заполнено")
    private String description;
    @NotNull(message = "Необходимо указать доступность вещи")
    private Boolean available;
    private Long ownerId;
    private Long requestId;
    private BookingDtoShort lastBooking;
    private BookingDtoShort nextBooking;
    private List<CommentDto> comments;

    public ItemDtoWithBookingsAndComments(Item item, BookingDtoShort lastBooking, BookingDtoShort nextBooking) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.available = item.getAvailable();
        this.ownerId = item.getOwner().getId();
        this.requestId = item.getRequest() == null ? null : item.getRequest().getId();
        this.comments = item.getComments().stream().map(itemMapper::commentEntityToDto).collect(Collectors.toList());
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }
}