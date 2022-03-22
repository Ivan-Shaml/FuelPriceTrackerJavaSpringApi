package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
