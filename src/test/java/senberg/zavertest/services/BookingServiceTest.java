package senberg.zavertest.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import senberg.zavertest.TestApplication;
import senberg.zavertest.model.Booking;
import senberg.zavertest.model.Room;
import senberg.zavertest.repositories.BookingRepository;
import senberg.zavertest.repositories.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplication.class)
@Transactional
public class BookingServiceTest {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void book(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);

        // WHEN
        Booking booking = bookingService.book(whiteRoom, LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"));

        // THEN
        assertNotNull(booking);
        assertNotNull(booking.getId());
        assertEquals(whiteRoom, booking.getRoom());
        assertEquals(LocalDate.parse("2020-01-04"), booking.getStartDate());
        assertEquals(LocalDate.parse("2020-01-06"), booking.getEndDate());
    }

    @Test
    public void bookWithWrongRoomId(){
        assertThrows(IllegalArgumentException.class, () -> bookingService.book(UUID.randomUUID(), LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06")));
    }

    @Test
    public void findBookingsWhileDatabaseEmpty(){
        // WHEN
        Set<Booking> bookings = bookingService.find(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"));

        // THEN
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }

    @Test
    public void findBookingsWhileDatabaseHasOne(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);
        Booking bookingThatCoversPeriod = new Booking(UUID.randomUUID(), whiteRoom, LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-10"));
        bookingRepository.save(bookingThatCoversPeriod);

        // WHEN
        Set<Booking> bookings = bookingService.find(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"));

        // THEN
        assertNotNull(bookings);
        assertEquals(1, bookings.size());
    }

    @Test
    public void findBookingsWhileDatabaseHasAllTypes(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);
        Booking bookingThatCoversPeriod = new Booking(UUID.randomUUID(), whiteRoom, LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-10"));
        bookingRepository.save(bookingThatCoversPeriod);
        Room orangeRoom = new Room(UUID.randomUUID(), "Orange Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(orangeRoom);
        Booking bookingThatEndsInPeriod = new Booking(UUID.randomUUID(), orangeRoom, LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-05"));
        bookingRepository.save(bookingThatEndsInPeriod);
        Room purpleRoom = new Room(UUID.randomUUID(), "Purple Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(purpleRoom);
        Booking bookingThatStartsInPeriod = new Booking(UUID.randomUUID(), purpleRoom, LocalDate.parse("2020-01-05"), LocalDate.parse("2020-01-10"));
        bookingRepository.save(bookingThatStartsInPeriod);

        // WHEN
        Set<Booking> bookings = bookingService.find(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"));

        // THEN
        assertNotNull(bookings);
        assertEquals(3, bookings.size());
    }

    @Test
    public void cancelBooking(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);
        Booking booking = new Booking(UUID.randomUUID(), whiteRoom, LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-10"));
        bookingRepository.save(booking);

        // WHEN
        bookingService.cancel(booking.getId());
        Set<Booking> bookings = bookingService.find(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"));

        // THEN
        assertNotNull(bookings);
        assertEquals(0, bookings.size());
    }
}
