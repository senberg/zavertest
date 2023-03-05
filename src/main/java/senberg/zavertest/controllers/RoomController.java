package senberg.zavertest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import senberg.zavertest.model.Room;
import senberg.zavertest.services.RoomService;

import java.time.LocalDate;
import java.util.Set;

@RestController
public class RoomController {
    @Autowired
    RoomService roomService;

    @GetMapping("/room/available/{startDate}/{endDate}/{beds}")
    public Set<Room> findAvailable(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable Integer beds){
        return roomService.findAvailable(startDate, endDate, beds);
    }
}
