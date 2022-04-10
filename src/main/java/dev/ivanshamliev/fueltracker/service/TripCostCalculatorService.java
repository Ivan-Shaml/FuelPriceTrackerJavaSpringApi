package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.FuelCostImperialReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelCostMetricReadDto;

import java.math.BigDecimal;

public interface TripCostCalculatorService {
    BigDecimal calculateFuelCostMetric(FuelCostMetricReadDto fuelCostMetricReadDto);

    BigDecimal calculateFuelCostImperial(FuelCostImperialReadDto fuelCostImperialReadDto);
}
