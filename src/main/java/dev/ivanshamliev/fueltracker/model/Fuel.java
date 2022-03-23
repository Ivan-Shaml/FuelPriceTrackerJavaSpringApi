package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class Fuel {
    @Id
    @SequenceGenerator(
            name = "fuel_sequence",
            sequenceName = "fuel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fuel_sequence"
    )
    private Integer id;
    private String name;
    private Double pricePerLiter;
    private LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.EAGER)
    private GasStation gasStation;

    public Fuel(String name, Double pricePerLiter, LocalDateTime lastUpdate, GasStation gasStation) {
        this.name = name;
        this.pricePerLiter = pricePerLiter;
        this.lastUpdate = lastUpdate;
        this.gasStation = gasStation;
    }
}
