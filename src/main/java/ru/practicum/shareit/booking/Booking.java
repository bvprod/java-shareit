package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.Instant;


@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_dttm")
    private Instant start;
    @Column(name = "end_dttm")
    private Instant end;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @OneToOne
    @JoinColumn(name = "booker_id")
    private User booker;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
