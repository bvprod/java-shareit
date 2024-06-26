package ru.practicum.shareit.booking;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserDto.UserDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class BookingMapper {
    public static BookingDto bookingToDto(Booking booking) {
        return new BookingDto(booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getBooker().getId(),
                booking.getStatus());
    }

    public static Booking dtoToBooking(BookingDto bookingDto, User booker, Item item) {
        return new Booking(bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                bookingDto.getStatus());
    }

}
