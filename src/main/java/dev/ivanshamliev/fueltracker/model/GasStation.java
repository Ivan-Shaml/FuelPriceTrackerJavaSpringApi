package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class GasStation {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String streetAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City location;

    public GasStation(String name, String streetAddress, City location) {
        this.name = name;
        this.streetAddress = streetAddress;
        this.location = location;
    }
}
