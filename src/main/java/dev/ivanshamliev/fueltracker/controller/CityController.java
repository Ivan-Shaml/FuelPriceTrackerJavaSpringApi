package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;
import dev.ivanshamliev.fueltracker.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/city/")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping()
    public ResponseEntity<List<City>> getAll() {
        var citiesList = cityService.getAllCities();
        return ResponseEntity.ok().body(citiesList);
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getById(@PathVariable Integer id) {
        var city = cityService.getById(id);
        return ResponseEntity.ok().body(city);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCity(@PathVariable Integer id, @RequestBody CityUpdateDto cityUpdateDto) {
        cityService.updateCity(id, cityUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<?> createCity(@RequestBody City city) {
        Integer newRecordId = cityService.addCity(city);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/city/" + newRecordId).toUriString());
        return ResponseEntity.created(uri).build();
    }
}
