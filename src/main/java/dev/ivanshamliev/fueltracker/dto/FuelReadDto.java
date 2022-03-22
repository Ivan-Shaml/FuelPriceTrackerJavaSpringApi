package dev.ivanshamliev.fueltracker.dto;

import dev.ivanshamliev.fueltracker.model.PriceHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public class FuelReadDto {
    private Integer id;
    private String name;
    private Double pricePerLiter;
    private LocalDateTime lastUpdate;
    private Collection<PriceHistory> priceHistory;
}
