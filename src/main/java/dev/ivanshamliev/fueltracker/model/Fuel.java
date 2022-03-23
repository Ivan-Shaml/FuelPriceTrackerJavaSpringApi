package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class Fuel {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double pricePerLiter;
    private LocalDateTime lastUpdate;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GasStation gasStation;

    public Fuel(String name, Double pricePerLiter, LocalDateTime lastUpdate, GasStation gasStation) {
        this.name = name;
        this.pricePerLiter = pricePerLiter;
        this.lastUpdate = lastUpdate;
        this.gasStation = gasStation;
    }
}
