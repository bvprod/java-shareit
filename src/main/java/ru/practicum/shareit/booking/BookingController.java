package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    BookingDtoOutput addBooking(@Valid @RequestBody BookingDto bookingDto,
                                @RequestHeader("X-Sharer-User-Id") long bookerId) {
        return bookingService.addBooking(bookingDto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    BookingDtoOutput decideBooking(@PathVariable(value = "bookingId") long bookingId,
                                   @RequestHeader("X-Sharer-User-Id") long ownerId,
                                   @RequestParam(value = "approved") boolean approved) {
        return bookingService.decideBooking(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    BookingDtoOutput getBooking(@PathVariable(value = "bookingId") long bookingId,
                                @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getBooking(bookingId, userId);
    }

    @GetMapping
    List<BookingDtoOutput> getUserBookings(@RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                           @RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingService.getUserBookings(userId, state);
    }

    @GetMapping("/owner")
    List<BookingDtoOutput> getUserItemBookings(@RequestParam(value = "state", defaultValue = "ALL") BookingState state,
                                               @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return bookingService.getUserItemBookings(ownerId, state);
    }

}
