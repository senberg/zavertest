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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestApplication.class)
@Transactional
public class RoomServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void getAvailableWhileDatabaseEmpty(){
        // WHEN
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.now(), LocalDate.now(), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(0, roomsAvailable.size());
    }

    @Test
    public void getAvailableWithNothingInSize(){
        // GIVEN
        Room blueRoom = new Room(UUID.randomUUID(), "Blue Room", "A nice room", 1, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(blueRoom);

        // WHEN
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.now(), LocalDate.now(), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(0, roomsAvailable.size());
    }

    @Test
    public void getAvailableWithOneInSize(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);

        // WHEN
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.now(), LocalDate.now(), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(1, roomsAvailable.size());
    }

    @Test
    public void getAvailableWithTwoInSize(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);
        Room orangeRoom = new Room(UUID.randomUUID(), "Orange Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(orangeRoom);

        // WHEN
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.now(), LocalDate.now(), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(2, roomsAvailable.size());
    }

    @Test
    public void getAvailableWhileBooked(){
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
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(0, roomsAvailable.size());
    }

    @Test
    public void getAvailableWhileNotBookedInCurrentRange(){
        // GIVEN
        Room whiteRoom = new Room(UUID.randomUUID(), "White Room", "A nice room", 2, BigDecimal.TEN, Currency.getInstance("SEK"));
        roomRepository.save(whiteRoom);
        Booking earlierBooking = new Booking(UUID.randomUUID(), whiteRoom, LocalDate.parse("2020-01-01"), LocalDate.parse("2020-01-02"));
        bookingRepository.save(earlierBooking);
        Booking laterBooking = new Booking(UUID.randomUUID(), whiteRoom, LocalDate.parse("2020-01-09"), LocalDate.parse("2020-01-10"));
        bookingRepository.save(laterBooking);

        // WHEN
        Set<Room> roomsAvailable = roomService.findAvailable(LocalDate.parse("2020-01-04"), LocalDate.parse("2020-01-06"), 2);

        // THEN
        assertNotNull(roomsAvailable);
        assertEquals(1, roomsAvailable.size());
    }
}
