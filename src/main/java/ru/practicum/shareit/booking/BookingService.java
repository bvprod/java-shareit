package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;

import java.util.List;

public interface BookingService {
    BookingDtoOutput addBooking(BookingDto bookingDto, long bookerId);

    BookingDtoOutput decideBooking(long bookingId, long ownerId, boolean approved);

    BookingDtoOutput getBooking(long bookingId, long userId);

    List<BookingDtoOutput> getUserBookings(long userId, BookingState state);

    List<BookingDtoOutput> getUserItemBookings(long ownerId, BookingState state);

    Booking findBooking(Long bookingId);
}
