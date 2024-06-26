package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBooker_idOrderByStartDesc(Long bookerId);

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


}
