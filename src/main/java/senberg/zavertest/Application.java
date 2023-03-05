package senberg.zavertest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import senberg.zavertest.model.Booking;
import senberg.zavertest.model.Room;
import senberg.zavertest.repositories.BookingRepository;
import senberg.zavertest.repositories.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.UUID;

@SpringBootApplication
public class Application implements InitializingBean {
	@Autowired
	RoomRepository roomRepository;
	@Autowired
	BookingRepository bookingRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void afterPropertiesSet() {
		Room penthouse = new Room(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Penthouse", "Pool included", 2, BigDecimal.valueOf(10000), Currency.getInstance("SEK"));
		roomRepository.save(penthouse);
		Booking christmasFun = new Booking(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), penthouse, LocalDate.parse("2020-12-20"), LocalDate.parse("2020-12-28"));
		bookingRepository.save(christmasFun);
	}
}
