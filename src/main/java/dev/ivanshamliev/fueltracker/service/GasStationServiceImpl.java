package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.GasStationCreateDto;
import dev.ivanshamliev.fueltracker.dto.GasStationUpdateDto;
import dev.ivanshamliev.fueltracker.exception.EntityNotFoundException;
import dev.ivanshamliev.fueltracker.exception.InvalidDataProvidedException;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.model.GasStation;
import dev.ivanshamliev.fueltracker.repository.CityRepository;
import dev.ivanshamliev.fueltracker.repository.FuelRepository;
import dev.ivanshamliev.fueltracker.repository.GasStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GasStationServiceImpl implements GasStationService {

    private final GasStationRepository gasStationRepository;
    private final CityRepository cityRepository;
    private final FuelRepository fuelRepository;

    @Override
    public List<GasStation> getAllByCity(String cityName) {
        if (!this.cityRepository.existsByName(cityName)) {
            throw new EntityNotFoundException("City with the specified name does not exists");
        }
        var allForCity = this.gasStationRepository.getAllByLocationName(cityName);

        if (allForCity.isEmpty()) {
            return new ArrayList<>();
        }
        return allForCity.get();
    }

    @Override
    public GasStation getById(Integer id) {
        var gasStation = this.gasStationRepository.findById(id);
        if (gasStation.isEmpty()) {
            throw new EntityNotFoundException("Gas station with the specified id does not exists");
        }

        return gasStation.get();
    }

    @Override
    public Integer addGasStation(GasStationCreateDto gasStationCreateDto) {

        if (gasStationCreateDto.getCityId() == null) {
            throw new InvalidDataProvidedException("City cannot be null.");
        }

        var cityFromDb = this.cityRepository.findById(gasStationCreateDto.getCityId())
                .orElseThrow(() -> new InvalidDataProvidedException("The city doesn't exist."));

        if (gasStationCreateDto.getName().isEmpty() && gasStationCreateDto.getName().isBlank()) {
            throw new InvalidDataProvidedException("The gas station's name cannot be null or empty.");
        }
        if (gasStationCreateDto.getStreetAddress().isEmpty() && gasStationCreateDto.getStreetAddress().isBlank()) {
            throw new InvalidDataProvidedException("The gas station's address cannot be null or empty.");
        }

        var newGasStation = new GasStation(gasStationCreateDto.getName(), gasStationCreateDto.getStreetAddress(), cityFromDb);

        this.gasStationRepository.save(newGasStation);
        log.info("Record for a new gas station has been created.");
        return newGasStation.getId();
    }

    @Override
    public void deleteGasStation(Integer id) {
        boolean gasStationExists = this.gasStationRepository.existsById(id);
        if (!gasStationExists) {
            throw new EntityNotFoundException("The gas station with the specified id was not found!");
        }

        this.gasStationRepository.deleteById(id);
        log.warn("Gas station with id {} has been deleted.", id);
    }

    @Override
    @Transactional
    public void updateGasStation(Integer id, GasStationUpdateDto gasStationUpdateDto) {

        var gasStationFromDb = this.gasStationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The gas station with the specified id was not found!"));

        if (gasStationUpdateDto.getCityId() == null) {
            throw new InvalidDataProvidedException("City cannot be null.");
        }
        var cityFromDb = this.cityRepository.findById(gasStationUpdateDto.getCityId())
                .orElseThrow(() -> new InvalidDataProvidedException("The city doesn't exist."));


        if (gasStationUpdateDto.getName() != null && !gasStationUpdateDto.getName().isEmpty() && !gasStationUpdateDto.getName().isBlank()) {
            gasStationFromDb.setName(gasStationUpdateDto.getName());
        } else {
            throw new InvalidDataProvidedException("The gas station's name cannot be null or empty.");
        }

        if (gasStationUpdateDto.getStreetAddress() != null && !gasStationUpdateDto.getStreetAddress().isEmpty() && !gasStationUpdateDto.getStreetAddress().isBlank()) {
            gasStationFromDb.setStreetAddress(gasStationUpdateDto.getStreetAddress());
        } else {
            throw new InvalidDataProvidedException("The gas station's address cannot be null or empty.");
        }

        gasStationFromDb.setLocation(cityFromDb);
        log.info("Gas station with id {} has been updated.", id);

    }

    @Override
    public List<Fuel> getAvailableFuel(Integer stationId) {

        var gasStation = this.gasStationRepository.findById(stationId)
                .orElseThrow(() -> new EntityNotFoundException("The gas station with the specified id was not found!"));

        var fuel = this.fuelRepository.getAllByGasStationId(stationId);

        if (fuel.isEmpty()) {
            return new ArrayList<>();
        }

        return fuel.get();
    }
}
