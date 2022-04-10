package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.FuelCostImperialReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelCostMetricReadDto;
import dev.ivanshamliev.fueltracker.exception.EntityNotFoundException;
import dev.ivanshamliev.fueltracker.repository.FuelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TripCostCalculatorServiceImpl implements TripCostCalculatorService {

    private final FuelRepository fuelRepository;

    @Override
    public BigDecimal calculateFuelCostMetric(FuelCostMetricReadDto fuelCostMetricReadDto) {

        Integer fuelId = fuelCostMetricReadDto.getFuelId();
        BigDecimal litersPerOneHundredKm = new BigDecimal(fuelCostMetricReadDto.getLitersPerOneHundredKm().toString())
                .setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal distanceInKm = new BigDecimal(fuelCostMetricReadDto.getDistanceInKm().toString())
                .setScale(2, RoundingMode.HALF_EVEN);

        var fuel = fuelRepository.findById(fuelId);
        if (fuel.isEmpty()) {
            throw new EntityNotFoundException("Fuel with the specified id does not exist.");
        }

        BigDecimal pricePerLiter = new BigDecimal(fuel.get().getPricePerLiter())
                .setScale(2, RoundingMode.HALF_EVEN);

        return distanceInKm.divide(new BigDecimal("100.00"), RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN)
                .multiply(litersPerOneHundredKm)
                .setScale(2, RoundingMode.HALF_EVEN)
                .multiply(pricePerLiter)
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal calculateFuelCostImperial(FuelCostImperialReadDto fuelCostImperialReadDto) {

        Integer fuelId = fuelCostImperialReadDto.getFuelId();
        BigDecimal milesPerGallon = new BigDecimal(fuelCostImperialReadDto.getMilesPerGallon().toString())
                .setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal distanceInMiles = new BigDecimal(fuelCostImperialReadDto.getDistanceInMiles().toString())
                .setScale(2, RoundingMode.HALF_EVEN);

        var fuel = fuelRepository.findById(fuelId);
        if (fuel.isEmpty()) {
            throw new EntityNotFoundException("Fuel with the specified id does not exist.");
        }

        final BigDecimal ONE_GALLON_IN_LITERS = new BigDecimal("3.785412");

        BigDecimal pricePerGallon = new BigDecimal(fuel.get().getPricePerLiter())
                .setScale(2, RoundingMode.HALF_EVEN)
                .multiply(ONE_GALLON_IN_LITERS)
                .setScale(2, RoundingMode.HALF_EVEN);

        return distanceInMiles.divide(milesPerGallon, RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN)
                .multiply(pricePerGallon)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
