package senberg.zavertest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="booking")
public class Booking {
    @Id
    private UUID id;
    @ManyToOne
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
}
