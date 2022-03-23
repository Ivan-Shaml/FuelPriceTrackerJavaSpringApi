package dev.ivanshamliev.fueltracker.controller;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;
import dev.ivanshamliev.fueltracker.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.InvalidParameterException;
import java.util.List;

@RestController @Slf4j @RequestMapping("/api/city/") @RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping()
    public ResponseEntity<List<City>> getAll() {
        var citiesList = cityService.getAllCities();
        return ResponseEntity.ok().body(citiesList);
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getById(@PathVariable Integer id){
        try {
            var city = cityService.getById(id);
            return ResponseEntity.ok().body(city);
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        try {
            cityService.deleteCity(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCity(@PathVariable Integer id, @RequestBody CityUpdateDto cityUpdateDto) {
        try {
            cityService.updateCity(id, cityUpdateDto);
            return ResponseEntity.noContent().build();
        } catch (InvalidParameterException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createCity(@RequestBody City city) {
        try {
            Integer newRecordId = cityService.addCity(city);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/city/" + newRecordId).toUriString());
            return ResponseEntity.created(uri).build();
        }catch (DataIntegrityViolationException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
