package senberg.zavertest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senberg.zavertest.model.Booking;
import senberg.zavertest.model.Room;
import senberg.zavertest.repositories.BookingRepository;
import senberg.zavertest.repositories.RoomRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RoomRepository roomRepository;

    public Booking book(UUID roomId, LocalDate startDate, LocalDate endDate){
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Could not find a room with the given id."));
        return book(room, startDate, endDate);
    }

    public Booking book(Room room, LocalDate startDate, LocalDate endDate){
        Booking booking = new Booking(UUID.randomUUID(), room, startDate, endDate);
        return bookingRepository.save(booking);
    }

    public Set<Booking> find(LocalDate startDate, LocalDate endDate){
        List<Booking> bookingsStartingDuringPeriod = bookingRepository.findAllByStartDateBetween(startDate.plusDays(1), endDate.plusDays(1));
        List<Booking> bookingsEndingDuringPeriod = bookingRepository.findAllByEndDateBetween(startDate.plusDays(1), endDate.plusDays(1));
        List<Booking> bookingsThatCoverPeriod = bookingRepository.findAllByStartDateBeforeAndEndDateAfter(startDate, endDate);
        Set<Booking> bookings = new HashSet<>();
        bookings.addAll(bookingsStartingDuringPeriod);
        bookings.addAll(bookingsEndingDuringPeriod);
        bookings.addAll(bookingsThatCoverPeriod);
        return bookings;
    }

    public void cancel(UUID bookingId){
        bookingRepository.deleteById(bookingId);
    }
}
