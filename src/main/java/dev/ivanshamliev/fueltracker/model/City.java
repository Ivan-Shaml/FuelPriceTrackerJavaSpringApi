package dev.ivanshamliev.fueltracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity @Data @Table @NoArgsConstructor @AllArgsConstructor
public class City {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public City(String name) {
        this.name = name;
    }
}
