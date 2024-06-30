package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithBookingsAndComments {
    private Long id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank(message = "Описание должно быть заполнено")
    private String description;
    @NotNull(message = "Необходимо указать доступность вещи")
    private Boolean available;
    private Long ownerId;
    private Long request;
    private BookingDtoShort lastBooking;
    private BookingDtoShort nextBooking;
    private List<CommentDto> comments;

    @JsonIgnore
    private final ItemMapper itemMapper = Mappers.getMapper(ItemMapper.class);

    public ItemDtoWithBookingsAndComments(Item item, BookingDtoShort lastBooking, BookingDtoShort nextBooking) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.available = item.getAvailable();
        this.ownerId = item.getOwner().getId();
        this.request = item.getRequest();
        this.comments = item.getComments().stream().map(itemMapper::commentEntityToDto).collect(Collectors.toList());
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }
}