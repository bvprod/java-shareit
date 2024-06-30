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
public class BookingDtoShort {
    private Long id;
    private Long bookerId;

    @JsonCreator
    public BookingDtoShort(@JsonProperty("id") Long id,
                            @JsonProperty("bookerId") Long bookerId) {
        this.id = id;
        this.bookerId = bookerId;
    }
}