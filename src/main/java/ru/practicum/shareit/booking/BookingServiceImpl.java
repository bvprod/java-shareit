package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public BookingDto addBooking(BookingDto bookingDto, long bookerId) {
        Item item = itemRepository
                .findById(bookingDto.getItemId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        User user = userRepository
                .findById(bookerId)
                .orElseThrow(() ->
                        new ObjectDoesNotExistException("Данного пользователя не существует"));
        return BookingMapper.bookingToDto(bookingRepository.save(BookingMapper.dtoToBooking(bookingDto, user, item)));
    }

    @Override
    public BookingDto decideBooking(long bookingId, long ownerId, boolean approved) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new ObjectDoesNotExistException("Данного бронирования не существует"));
        if (booking.getBooker().getId().equals(ownerId)) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            bookingRepository.save(booking);
        } else {
            throw new WrongOwnerException("Эта вещь не принадлежит данному пользователю");
        }
        return null;
    }

    @Override
    public BookingDto getBooking(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ObjectDoesNotExistException("Данного бронирования не существует"));
        if (booking.getBooker().getId() != userId || booking.getItem().getOwner().getId() != userId) {
            throw new WrongOwnerException("У данного пользователя нет доступа к этому бронированию");
        }
        return BookingMapper.bookingToDto(booking);
    }

    @Override
    public List<BookingDto> getUserBookings(long userId, BookingState state) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Данного пользователя не существует"));
        switch (state) {
            case ALL:
                return bookingRepository.findByBooker_idOrderByStartDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findPastBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findFutureBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findCurrentBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findWaitingBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findRejectedBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(BookingMapper::bookingToDto)
                        .collect(Collectors.toList());
            default:
                throw new ObjectDoesNotExistException("Данный статус бронирования не поддерживается");
        }
    }

    @Override
    public List<BookingDto> getUserItemBookings(long ownerId, BookingState state) {
        return null;
    }
}
