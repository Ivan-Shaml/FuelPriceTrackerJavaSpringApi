package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.model.GasStation;

import java.util.List;

public interface GasStationService {
    List<GasStation> getAllByCity(String cityName);
    GasStation getById(Integer id);
    void addGasStation(GasStation gasStation);
    void deleteGasStation(Integer id);
    void updateGasStation(Integer id, GasStationUpdateDto gasStation);
    List<Fuel> getAvailableFuel(Integer stationId);
}
