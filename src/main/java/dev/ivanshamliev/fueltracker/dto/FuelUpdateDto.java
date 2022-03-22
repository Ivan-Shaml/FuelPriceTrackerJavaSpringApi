package dev.ivanshamliev.fueltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @AllArgsConstructor @NoArgsConstructor
public class FuelUpdateDto {
    @NonNull
    private String name;
    @NonNull
    private Double pricePerLiter;
    @NonNull
    private Integer gasStationId;
}
