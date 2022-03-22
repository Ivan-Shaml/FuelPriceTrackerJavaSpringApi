package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.model.GasStation;
import dev.ivanshamliev.fueltracker.repository.CityRepository;
import dev.ivanshamliev.fueltracker.repository.FuelRepository;
import dev.ivanshamliev.fueltracker.repository.GasStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class GasStationServiceImpl implements GasStationService{

    private final GasStationRepository gasStationRepository;
    private final CityRepository cityRepository;
    private final FuelRepository fuelRepository;

    @Override
    public List<GasStation> getAllByCity(String cityName) {
        if (!this.cityRepository.existsByName(cityName)) {
            throw new InvalidParameterException("City with the specified name does not exists");
        }
        var allForCity = this.gasStationRepository.getAllByLocationName(cityName);

        if (allForCity.isEmpty()) {
            throw new NegativeArraySizeException("This city doesn't have any gas stations yet.");
        }
        return allForCity.get();
    }

    @Override
    public GasStation getById(Integer id) {
        var gasStation = this.gasStationRepository.findById(id);
        if (gasStation.isEmpty()) {
            throw new InvalidParameterException("City with the specified name does not exists");
        }

        return gasStation.get();
    }

    @Override
    public void addGasStation(GasStation gasStation) {
        this.gasStationRepository.save(gasStation);
        log.info("Record for a new gas station has been created.");
    }

    @Override
    public void deleteGasStation(Integer id) {
        boolean gasStationExists = this.gasStationRepository.existsById(id);
        if (!gasStationExists) {
            throw new InvalidParameterException("The gas station with the specified id was not found!");
        }

        this.gasStationRepository.deleteById(id);
        log.warn("Gas station with id {} has been deleted.", id);
    }

    @Override @Transactional
    public void updateGasStation(Integer id, GasStationUpdateDto gasStationUpdateDto) {
        boolean doesGasStationExist = this.gasStationRepository.existsById(id);
        var cityFromDb = this.cityRepository.findById(gasStationUpdateDto.getCityId());
        if (cityFromDb.isEmpty()) {
            throw new InvalidParameterException("The city doesn't exist.");
        }
        if (!doesGasStationExist) {
            throw new InvalidParameterException("The gas station with the specified id was not found!");
        }

        var gasStationFromDb = this.gasStationRepository.getById(id);

        if (gasStationUpdateDto.getName() != null && !gasStationUpdateDto.getName().isEmpty() && !gasStationUpdateDto.getName().isBlank()) {
            gasStationFromDb.setName(gasStationUpdateDto.getName());
        } else {
            throw new InvalidParameterException("The gas station's name cannot be null or empty.");
        }

        if (gasStationUpdateDto.getStreetAddress() != null && !gasStationUpdateDto.getStreetAddress().isEmpty() && !gasStationUpdateDto.getStreetAddress().isBlank()) {
            gasStationFromDb.setStreetAddress(gasStationUpdateDto.getStreetAddress());
        } else {
            throw new InvalidParameterException("The gas station's address cannot be null or empty.");
        }

        gasStationFromDb.setLocation(cityFromDb.get());
        log.info("Gas station with id {} has been updated.", id);

    }

    @Override
    public List<Fuel> getAvailableFuel(Integer stationId) {
        var fuel = this.fuelRepository.getAllByGasStationId(stationId);
        if (fuel.isEmpty()){
            throw new NegativeArraySizeException("This gas station is out of fuel.");
        }

        return fuel.get();
    }
}
