package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

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