package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.awt.print.Book;
import java.util.List;

public interface BookingService {
    BookingDto addBooking(BookingDto bookingDto, long bookerId);
    BookingDto decideBooking(long bookingId, long ownerId, boolean approved);
    BookingDto getBooking(long bookingId, long userId);
    List<BookingDto> getUserBookings(long userId, BookingState state);
    List<BookingDto> getUserItemBookings(long ownerId, BookingState state);
}
