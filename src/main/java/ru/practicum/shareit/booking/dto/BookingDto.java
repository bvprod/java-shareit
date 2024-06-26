package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    @NotNull(message = "Должна быть указана дата и время начала бронирования")
    private Instant start;
    @NotNull(message = "Должна быть указана дата и время конца бронирования")
    private Instant end;
    @NotNull(message = "Должен быть указан id бронируемой вещи")
    private Long itemId;
    private Long bookerId;
    private BookingStatus status = BookingStatus.WAITING;
}
