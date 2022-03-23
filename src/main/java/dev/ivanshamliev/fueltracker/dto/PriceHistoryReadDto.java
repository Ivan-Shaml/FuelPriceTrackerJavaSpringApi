package dev.ivanshamliev.fueltracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class PriceHistoryReadDto {
    @JsonIgnore
    private Integer id;
    private LocalDateTime timeUpdated;
    private Double oldPrice;
    private Double newPrice;
}
