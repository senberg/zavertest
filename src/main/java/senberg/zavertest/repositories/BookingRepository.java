package senberg.zavertest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senberg.zavertest.model.Booking;
import senberg.zavertest.model.Room;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findAllByRoomInAndStartDateBetween(Collection<Room> rooms, LocalDate date1, LocalDate date2);
    List<Booking> findAllByRoomInAndEndDateBetween(Collection<Room> rooms, LocalDate date1, LocalDate date2);
    List<Booking> findAllByRoomInAndStartDateBeforeAndEndDateAfter(Collection<Room> rooms, LocalDate startDate, LocalDate endDate);
    List<Booking> findAllByStartDateBetween(LocalDate date1, LocalDate date2);
    List<Booking> findAllByEndDateBetween(LocalDate date1, LocalDate date2);
    List<Booking> findAllByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
    Booking save(Booking booking);
    void deleteById(UUID id);
}