package senberg.zavertest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import senberg.zavertest.model.Booking;
import senberg.zavertest.services.BookingService;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RestController
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping("/booking/{roomId}/{startDate}/{endDate}")
    public Booking book(@PathVariable UUID roomId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return bookingService.book(roomId, startDate, endDate);
    }

    @GetMapping("/booking/{startDate}/{endDate}")
    public Set<Booking> find(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate){
        return bookingService.find(startDate, endDate);
    }

    @DeleteMapping("/booking/{id}")
    public void cancel(@PathVariable UUID id){
        bookingService.cancel(id);
    }
}
