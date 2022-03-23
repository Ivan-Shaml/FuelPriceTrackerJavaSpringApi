package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class GasStation {
    @Id
    @SequenceGenerator(
            name = "gas_station_sequence",
            sequenceName = "gas_station_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "gas_station_sequence"
    )
    private Integer id;

    private String name;
    private String streetAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    private City location;

    public GasStation(String name, String streetAddress, City location) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.location = location;
    }
}
