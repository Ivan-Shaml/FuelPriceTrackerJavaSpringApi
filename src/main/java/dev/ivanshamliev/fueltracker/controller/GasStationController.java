package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.GasStationCreateDto;
import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.model.GasStation;
import dev.ivanshamliev.fueltracker.service.GasStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.InvalidParameterException;

@RestController @Slf4j @RequestMapping("/api/station/") @RequiredArgsConstructor
public class GasStationController {
    private final GasStationService gasStationService;

    @GetMapping("{id}")
    public ResponseEntity<GasStation> getById(@PathVariable Integer id) {
        try {
            var gasStation = gasStationService.getById(id);
            return ResponseEntity.ok().body(gasStation);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("city/{cityName}")
    public ResponseEntity<?> getByCity(@PathVariable String cityName) {
        try {
            var gasStations = gasStationService.getAllByCity(cityName);
            return ResponseEntity.ok().body(gasStations);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (NegativeArraySizeException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.ok().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createStation(@RequestBody GasStationCreateDto gasStationCreateDto){
        try {
            Integer newRecordId = gasStationService.addGasStation(gasStationCreateDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/station/" + newRecordId).toUriString());
            return ResponseEntity.created(uri).build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeStation(@PathVariable Integer id) {
        try {
            gasStationService.deleteGasStation(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateGasStation(@PathVariable Integer id, @RequestBody GasStationUpdateDto gasStationUpdateDto) {
        try {
            gasStationService.updateGasStation(id, gasStationUpdateDto);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("{id}/fuel")
    public ResponseEntity<?> getAvailableFuel(@PathVariable Integer id) {
        try {
            var fuel = gasStationService.getAvailableFuel(id);
            return ResponseEntity.ok().body(fuel);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (NegativeArraySizeException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.ok().body(ex.getMessage());
        }
    }
}
