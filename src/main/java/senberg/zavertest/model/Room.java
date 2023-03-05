package senberg.zavertest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="room")
public class Room {
    @Id
    private UUID id;
    private String name;
    private String description;
    private Integer beds;
    private BigDecimal pricePerNight;
    private Currency currency;
}
