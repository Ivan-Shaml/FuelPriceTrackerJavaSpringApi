package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.GasStationCreateDto;
import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.model.GasStation;
import dev.ivanshamliev.fueltracker.service.GasStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/station/")
@RequiredArgsConstructor
public class GasStationController {
    private final GasStationService gasStationService;

    @GetMapping("{id}")
    public ResponseEntity<GasStation> getById(@PathVariable Integer id) {
        var gasStation = gasStationService.getById(id);
        return ResponseEntity.ok().body(gasStation);
    }


    @GetMapping("city/{cityName}")
    public ResponseEntity<?> getByCity(@PathVariable String cityName) {
        var gasStations = gasStationService.getAllByCity(cityName);
        return ResponseEntity.ok().body(gasStations);
    }

    @PostMapping()
    public ResponseEntity<?> createStation(@RequestBody GasStationCreateDto gasStationCreateDto) {
        Integer newRecordId = gasStationService.addGasStation(gasStationCreateDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/station/" + newRecordId).toUriString());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeStation(@PathVariable Integer id) {
        gasStationService.deleteGasStation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateGasStation(@PathVariable Integer id, @RequestBody GasStationUpdateDto gasStationUpdateDto) {
        gasStationService.updateGasStation(id, gasStationUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/fuel")
    public ResponseEntity<?> getAvailableFuel(@PathVariable Integer id) {
        var fuel = gasStationService.getAvailableFuel(id);
        return ResponseEntity.ok().body(fuel);
    }
}
