package senberg.zavertest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import senberg.zavertest.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    List<Room> findAllByBeds(int beds);
}