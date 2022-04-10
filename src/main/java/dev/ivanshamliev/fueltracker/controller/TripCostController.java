package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.FuelCostImperialReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelCostMetricReadDto;
import dev.ivanshamliev.fueltracker.service.TripCostCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/trip/")
@RequiredArgsConstructor
public class TripCostController {

    private final TripCostCalculatorService tripCostService;

    @PutMapping("metric/")
    public ResponseEntity<?> calculateFuelCostMetric(@RequestBody FuelCostMetricReadDto fuelCostMetricReadDto) {
        var cost = tripCostService.calculateFuelCostMetric(fuelCostMetricReadDto);

        Object tripCost = new Object() {
            public final BigDecimal tripCost = cost;
        };

        return ResponseEntity.ok().body(tripCost);
    }

    @PutMapping("imperial/")
    public ResponseEntity<?> calculateFuelCostImperial(@RequestBody FuelCostImperialReadDto fuelCostImperialReadDto) {
        var cost = tripCostService.calculateFuelCostImperial(fuelCostImperialReadDto);

        Object tripCost = new Object() {
            public final BigDecimal tripCost = cost;
        };

        return ResponseEntity.ok().body(tripCost);
    }

}
