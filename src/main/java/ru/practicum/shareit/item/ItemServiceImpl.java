package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.exceptionHandler.exceptions.CommentForbiddenException;
import ru.practicum.shareit.exceptionHandler.exceptions.ObjectDoesNotExistException;
import ru.practicum.shareit.exceptionHandler.exceptions.WrongOwnerException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBookingsAndComments;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    private final ItemMapper mapper = Mappers.getMapper(ItemMapper.class);

    public ItemServiceImpl(ItemRepository itemRepository,
                           UserRepository userRepository,
                           BookingRepository bookingRepository,
                           CommentRepository commentRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public ItemDto addNewItem(ItemDto itemDto, Long ownerId) {
        User owner = userRepository
                .findById(ownerId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        return mapper.entityToDto(itemRepository.save(mapper.dtoToEntity(itemDto, owner)));
    }

    @Override
    @Transactional
    public ItemDto updateItem(Long itemId, Long ownerId, ItemDto itemDto) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new WrongOwnerException("Данный пользователь не является владельцем этой вещи");
        }
        mapper.updateItemFromDto(itemDto, item);
        return mapper.entityToDto(itemRepository.save(item));
    }

    @Override
    public ItemDtoWithBookingsAndComments getItem(Long itemId, Long userId) {
        userRepository
                .findById(userId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        if (item.getOwner().getId().equals(userId)) {
            return findLastAndNextItemBookings(item);
        } else return new ItemDtoWithBookingsAndComments(item, null, null);
    }

    @Override
    public List<ItemDtoWithBookingsAndComments> getAllItems(Long ownerId) {
        userRepository
                .findById(ownerId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        List<Item> items = itemRepository.findByOwnerIdOrderByIdAsc(ownerId);
        return items.stream().map(this::findLastAndNextItemBookings).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchForItems(String query) {
        return itemRepository.search(query).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto addComment(Long itemId, Long authorId, CommentDto commentDto) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Вещь с данным id не существует"));
        User author = userRepository
                .findById(authorId)
                .orElseThrow(() -> new ObjectDoesNotExistException("Пользователь с данным id не существует"));
        bookingRepository.findByBooker_idOrderByStartDesc(authorId).stream()
                .filter(b -> b.getItem().getId().equals(itemId) && b.getStatus() == BookingStatus.APPROVED
                        && b.getEnd().isBefore(LocalDateTime.now()))
                .findAny()
                .orElseThrow(() -> new CommentForbiddenException("Пользователь не может оставлять комментарий под вещью, " +
                        "которую не брал в аренду"));
        return mapper.commentEntityToDto(commentRepository.save(mapper.commentDtoToEntity(commentDto, author, item)));
    }

    @Transactional
    private ItemDtoWithBookingsAndComments findLastAndNextItemBookings(Item item) {
        List<Booking> bookings = bookingRepository.findByItem_idOrderByStartDesc(item.getId());
        if (bookings.isEmpty()) {
            return new ItemDtoWithBookingsAndComments(item, null, null);
        }
        LocalDateTime currentTimestamp = LocalDateTime.now();
        log.info("Время операции: " + currentTimestamp);
        BookingDtoShort lastBooking = bookings.stream()
                .filter(b -> b.getStart().isBefore(currentTimestamp) && b.getStatus() == BookingStatus.APPROVED)
                .max(Comparator.comparing(Booking::getEnd))
                .map(bookingMapper::entityToShortDto)
                .orElse(null);

        BookingDtoShort nextBooking = bookings.stream()
                .filter(b -> b.getStart().isAfter(currentTimestamp) && b.getStatus() == BookingStatus.APPROVED)
                .min(Comparator.comparing(Booking::getStart))
                .map(bookingMapper::entityToShortDto)
                .orElse(null);
        return new ItemDtoWithBookingsAndComments(item, lastBooking, nextBooking);
    }
}
