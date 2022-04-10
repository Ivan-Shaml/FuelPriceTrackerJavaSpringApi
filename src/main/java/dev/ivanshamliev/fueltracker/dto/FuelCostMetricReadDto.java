package dev.ivanshamliev.fueltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelCostMetricReadDto {
    @NonNull
    private Double litersPerOneHundredKm;
    @NonNull
    private Double distanceInKm;
    @NonNull
    private Integer fuelId;
}
