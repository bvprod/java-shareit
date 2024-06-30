package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(" select b from Booking b " +
            " where b.booker.id = ?1 " +
            " order by b.start desc")
    List<Booking> findByBooker_idOrderByStartDesc(Long bookerId);

    @Query(" select b from Booking b " +
            " where b.item.id = ?1 " +
            " order by b.start desc")
    List<Booking> findByItem_idOrderByStartDesc(Long itemId);

    @Query("select b from Booking b " +
            "where b.start < current_timestamp and b.end > current_timestamp " +
            "and b.booker.id = ?1 " +
            "order by b.start desc")
    List<Booking> findCurrentBookingsByBookerIdOrderByStartDttmDesc(Long bookerId);

    @Query("select b from Booking b " +
            "where b.start < current_timestamp and b.end < current_timestamp " +
            "and b.booker.id = ?1 " +
            "order by b.start desc")
    List<Booking> findPastBookingsByBookerIdOrderByStartDttmDesc(Long bookerId);

    @Query("select b from Booking b " +
            "where b.start > current_timestamp and b.end > current_timestamp " +
            "and b.booker.id = ?1 " +
            "order by b.start desc")
    List<Booking> findFutureBookingsByBookerIdOrderByStartDttmDesc(Long bookerId);

    @Query("select b from Booking b " +
            "where b.start > current_timestamp and b.end > current_timestamp and b.status = 'WAITING' " +
            "and b.booker.id = ?1 " +
            "order by b.start desc")
    List<Booking> findWaitingBookingsByBookerIdOrderByStartDttmDesc(Long bookerId);

    @Query("select b from Booking b " +
            "where b.start > current_timestamp and b.end > current_timestamp and b.status = 'REJECTED' " +
            "and b.booker.id = ?1 " +
            "order by b.start desc")
    List<Booking> findRejectedBookingsByBookerIdOrderByStartDttmDesc(Long bookerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "order by b.start desc")
    List<Booking> findAllBookingsForUserItem(Long ownerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and b.start < current_timestamp and b.end > current_timestamp " +
            "order by b.start desc")
    List<Booking> findCurrentBookingsForUserItem(Long ownerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and b.start < current_timestamp and b.end < current_timestamp " +
            "order by b.start desc")
    List<Booking> findPastBookingsForUserItem(Long ownerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and b.start > current_timestamp and b.end > current_timestamp " +
            "order by b.start desc")
    List<Booking> findFutureBookingsForUserItem(Long ownerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and b.start > current_timestamp and b.end > current_timestamp and b.status = 'WAITING' " +
            "order by b.start desc")
    List<Booking> findWaitingBookingsForUserItem(Long ownerId);

    @Query("select b from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and b.start > current_timestamp and b.end > current_timestamp and b.status = 'REJECTED' " +
            "order by b.start desc")
    List<Booking> findRejectedBookingsForUserItem(Long ownerId);
}
