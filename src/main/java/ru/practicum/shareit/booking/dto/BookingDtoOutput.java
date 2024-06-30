package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptionHandler.exceptions.InvalidBookingDateException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDtoOutput {
    private Long id;
    @NotNull(message = "Должна быть указана дата и время начала бронирования")
    @Future
    private LocalDateTime start;
    @NotNull(message = "Должна быть указана дата и время конца бронирования")
    @Future
    private LocalDateTime end;
    @NotNull(message = "Должен быть указан id бронируемой вещи")
    private Item item;
    private User booker;
    private BookingStatus status = BookingStatus.WAITING;

    @JsonCreator
    public BookingDtoOutput(@JsonProperty("id") Long id,
                            @JsonProperty("start") LocalDateTime start,
                            @JsonProperty("end") LocalDateTime end,
                            @JsonProperty("item") Item item,
                            @JsonProperty("booker") User booker,
                            @JsonProperty("status") BookingStatus status) {
        if (!end.isAfter(start)) {
            throw new InvalidBookingDateException("Дата начала бронирования должна быть раньше даты конца бронирования");
        }
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }
}
