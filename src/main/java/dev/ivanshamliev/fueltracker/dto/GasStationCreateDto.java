package dev.ivanshamliev.fueltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class GasStationCreateDto {
    @NonNull
    private String name;
    @NonNull
    private String streetAddress;
    @NonNull
    private Integer cityId;
}
