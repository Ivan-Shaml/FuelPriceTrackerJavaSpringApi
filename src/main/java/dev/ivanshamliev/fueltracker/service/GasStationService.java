package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.GasStationCreateDto;
import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.model.GasStation;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface GasStationService {
    List<GasStation> getAllByCity(String cityName);
    GasStation getById(Integer id);
    Integer addGasStation(GasStationCreateDto gasStation);
    void deleteGasStation(Integer id);
    void updateGasStation(Integer id, GasStationUpdateDto gasStation) throws DataIntegrityViolationException;
    List<Fuel> getAvailableFuel(Integer stationId);
}
