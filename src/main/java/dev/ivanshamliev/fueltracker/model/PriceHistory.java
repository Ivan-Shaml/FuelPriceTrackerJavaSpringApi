package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime timeUpdated;
    private Double oldPrice;
    private Double newPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Fuel fuel;

    public PriceHistory(LocalDateTime timeUpdated, Double oldPrice, Double newPrice, Fuel fuel) {
        this.timeUpdated = timeUpdated;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.fuel = fuel;
    }
}
