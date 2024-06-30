package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.exceptionHandler.exceptions.*;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

    @Override
    @Transactional
    public BookingDtoOutput addBooking(BookingDto bookingDto, long bookerId) {
        Item item = itemRepository
                .findById(bookingDto.getItemId())
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        if (!item.getAvailable()) {
            throw new ItemNotAvailableException("Эта вещь недоступна для бронирования");
        }
        User user = userRepository
                .findById(bookerId)
                .orElseThrow(() ->
                        new ObjectDoesNotExistException("Данного пользователя не существует"));
        if (item.getOwner().getId().equals(bookerId)){
            throw new WrongOwnerException("Нельзя бронировать свою же вещь");
        }
        return mapper.entityToOutputDto(bookingRepository.save(mapper.dtoToEntity(bookingDto, item, user)));
    }

    @Override
    @Transactional
    public BookingDtoOutput decideBooking(long bookingId, long ownerId, boolean approved) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() ->
                        new ObjectDoesNotExistException("Данного бронирования не существует"));
        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new BookingAlreadyApprovedException("Бронирование в статусе approved нельзя менять");
        }
        if (booking.getItem().getOwner().getId().equals(ownerId)) {
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            return mapper.entityToOutputDto(bookingRepository.save(booking));
        } else {
            throw new WrongOwnerException("Эта вещь не принадлежит данному пользователю");
        }
    }

    @Override
    public BookingDtoOutput getBooking(long bookingId, long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() ->
                new ObjectDoesNotExistException("Данного бронирования не существует"));
        if (booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId) {
            return mapper.entityToOutputDto(booking);
        } else {
            throw new WrongOwnerException("У данного пользователя нет доступа к этому бронированию");
        }
    }

    @Override
    public List<BookingDtoOutput> getUserBookings(long userId, BookingState state) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Данного пользователя не существует"));
        switch (state) {
            case ALL:
                return bookingRepository.findByBooker_idOrderByStartDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findPastBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findFutureBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findCurrentBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findWaitingBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findRejectedBookingsByBookerIdOrderByStartDttmDesc(userId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            default:
                throw new ObjectDoesNotExistException("Данный статус бронирования не поддерживается");
        }
    }

    @Override
    public List<BookingDtoOutput> getUserItemBookings(long ownerId, BookingState state) {
        userRepository
                .findById(ownerId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Данного пользователя не существует"));
        switch (state) {
            case ALL:
                return bookingRepository.findAllBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case PAST:
                return bookingRepository.findPastBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case FUTURE:
                return bookingRepository.findFutureBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case CURRENT:
                return bookingRepository.findCurrentBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case WAITING:
                return bookingRepository.findWaitingBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            case REJECTED:
                return bookingRepository.findRejectedBookingsForUserItem(ownerId)
                        .stream()
                        .map(mapper::entityToOutputDto)
                        .collect(Collectors.toList());
            default:
                throw new ObjectDoesNotExistException("Данный статус бронирования не поддерживается");
        }
    }
}
