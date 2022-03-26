package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.FuelCreateDto;
import dev.ivanshamliev.fueltracker.dto.FuelReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.service.FuelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/fuel/")
@RequiredArgsConstructor
public class FuelController {
    private final FuelService fuelService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(fuelService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Fuel> getById(@PathVariable Integer id) {
        var fuel = fuelService.getById(id);
        return ResponseEntity.ok().body(fuel);
    }

    @GetMapping("{id}/price")
    public ResponseEntity<FuelReadDto> getPriceById(@PathVariable Integer id) {
        var fuel = fuelService.mapToReadDto(id);
        return ResponseEntity.ok().body(fuel);
    }

    @GetMapping("{id}/price/{date}")
    public ResponseEntity<FuelReadDto> getPriceById(@PathVariable("id") Integer id, @PathVariable("date") String date) {
        var fuel = fuelService.getForDateToReadDto(id, date);
        return ResponseEntity.ok().body(fuel);
    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody FuelCreateDto fuelCreateDto) {
        Integer newRecordId = fuelService.addFuel(fuelCreateDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/fuel/" + newRecordId).toUriString());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        fuelService.deleteFuel(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateFuel(@PathVariable Integer id, @RequestBody FuelUpdateDto fuelUpdateDto) {
        fuelService.updateFuel(id, fuelUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/price/{newValue}")
    public ResponseEntity<?> updateFuel(@PathVariable Integer id, @PathVariable("newValue") Double newPrice) {
        fuelService.updatePrice(id, newPrice);
        return ResponseEntity.noContent().build();
    }
}
