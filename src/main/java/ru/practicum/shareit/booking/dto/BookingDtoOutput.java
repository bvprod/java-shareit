package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptionHandler.exceptions.InvalidBookingDateException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserDto.UserDto;

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
    private ItemDto item;
    private UserDto booker;
    private BookingStatus status = BookingStatus.WAITING;

    @JsonCreator
    public BookingDtoOutput(@JsonProperty("id") Long id,
                            @JsonProperty("start") LocalDateTime start,
                            @JsonProperty("end") LocalDateTime end,
                            @JsonProperty("item") ItemDto item,
                            @JsonProperty("booker") UserDto booker,
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
