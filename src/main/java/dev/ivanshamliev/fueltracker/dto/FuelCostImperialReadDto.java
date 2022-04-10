package dev.ivanshamliev.fueltracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelCostImperialReadDto {
    @NonNull
    private Double milesPerGallon;
    @NonNull
    private Double distanceInMiles;
    @NonNull
    private Integer fuelId;
}
