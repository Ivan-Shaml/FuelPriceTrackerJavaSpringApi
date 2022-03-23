package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.FuelCreateDto;
import dev.ivanshamliev.fueltracker.dto.FuelReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;

import java.util.List;

public interface FuelService {
    List<Fuel> getAll();
    Fuel getById(Integer id);
    void addFuel(FuelCreateDto fuelCreateDto);
    void deleteFuel(Integer id);
    void updatePrice(Integer fuelId, Double newPrice);
    void updateFuel(Integer id, FuelUpdateDto fuelUpdateDto);
    FuelReadDto mapToReadDto(Integer id);
}
