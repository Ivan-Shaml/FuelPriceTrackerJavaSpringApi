package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Double pricePerLiter;
    private LocalDate lastUpdate;

    @ManyToOne(fetch = FetchType.EAGER)
    private GasStation gasStation;
}
