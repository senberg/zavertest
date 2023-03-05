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
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    BookingRepository bookingRepository;

    public Set<Room> findAvailable(LocalDate startDate, LocalDate endDate, Integer beds){
        List<Room> roomsWithCorrectNumberOfBeds = roomRepository.findAllByBeds(beds);
        List<Booking> bookingsThatStartDuringPeriod = bookingRepository.findAllByRoomInAndStartDateBetween(roomsWithCorrectNumberOfBeds, startDate.minusDays(1), endDate.plusDays(1));
        List<Booking> bookingsThatEndDuringPeriod = bookingRepository.findAllByRoomInAndEndDateBetween(roomsWithCorrectNumberOfBeds, startDate.minusDays(1), endDate.plusDays(1));
        List<Booking> bookingsThatContainPeriod = bookingRepository.findAllByRoomInAndStartDateBeforeAndEndDateAfter(roomsWithCorrectNumberOfBeds, startDate, endDate);
        Set<Room> bookedRooms = new HashSet<>();
        bookedRooms.addAll(bookingsThatStartDuringPeriod.stream().map(Booking::getRoom).collect(Collectors.toSet()));
        bookedRooms.addAll(bookingsThatEndDuringPeriod.stream().map(Booking::getRoom).collect(Collectors.toSet()));
        bookedRooms.addAll(bookingsThatContainPeriod.stream().map(Booking::getRoom).collect(Collectors.toSet()));
        return roomsWithCorrectNumberOfBeds.stream().filter(room -> !bookedRooms.contains(room)).collect(Collectors.toSet());
    }
}
