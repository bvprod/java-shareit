package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.exceptionHandler.exceptions.InvalidBookingDateException;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDto {
    private Long id;
    @NotNull(message = "Должна быть указана дата и время начала бронирования")
    @Future
    private LocalDateTime start;
    @NotNull(message = "Должна быть указана дата и время конца бронирования")
    @Future
    private LocalDateTime end;
    @NotNull(message = "Должен быть указан id бронируемой вещи")
    private Long itemId;
    private Long bookerId;
    private BookingStatus status = BookingStatus.WAITING;

    @JsonCreator
    public BookingDto(@JsonProperty("id") Long id,
                      @JsonProperty("start") LocalDateTime start,
                      @JsonProperty("end") LocalDateTime end,
                      @JsonProperty("itemId") Long itemId,
                      @JsonProperty("bookerId") Long bookerId) {
        if (!end.isAfter(start)) {
            throw new InvalidBookingDateException("Дата начала бронирования должна быть раньше даты конца бронирования");
        }
        this.id = id;
        this.start = start;
        this.end = end;
        this.itemId = itemId;
        this.bookerId = bookerId;
    }
}
