package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.FuelCreateDto;
import dev.ivanshamliev.fueltracker.dto.FuelReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.service.FuelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.InvalidParameterException;

@RestController @Slf4j @RequestMapping("/api/fuel/") @RequiredArgsConstructor
public class FuelController {
    private final FuelService fuelService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(fuelService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Fuel> getById(@PathVariable Integer id) {
        try {
            var fuel = fuelService.getById(id);
            return ResponseEntity.ok().body(fuel);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}/price")
    public ResponseEntity<FuelReadDto> getPriceById(@PathVariable Integer id) {
        try {
            var fuel = fuelService.mapToReadDto(id);
            return ResponseEntity.ok().body(fuel);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody FuelCreateDto fuelCreateDto) {
        try {
            fuelService.addFuel(fuelCreateDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/fuel/").toUriString());

            return ResponseEntity.created(uri).build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            fuelService.deleteFuel(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateFuel(@PathVariable Integer id, @RequestBody FuelUpdateDto fuelUpdateDto) {
        try {
            fuelService.updateFuel(id, fuelUpdateDto);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @PutMapping("{id}/price/{value}")
    public ResponseEntity<?> updateFuel(@PathVariable Integer id, @PathVariable("value") Double newPrice) {
        try {
            fuelService.updatePrice(id, newPrice);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
