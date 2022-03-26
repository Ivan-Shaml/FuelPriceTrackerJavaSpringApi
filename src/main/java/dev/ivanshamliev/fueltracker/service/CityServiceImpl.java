package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.exception.DuplicateEntityException;
import dev.ivanshamliev.fueltracker.exception.EntityNotFoundException;
import dev.ivanshamliev.fueltracker.exception.InvalidDataProvidedException;
import dev.ivanshamliev.fueltracker.model.City;
import dev.ivanshamliev.fueltracker.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public City getByName(String name) {
        var city = this.cityRepository.getByName(name);
        if (city == null) {
            throw new EntityNotFoundException("The city with the specified name was not found!");
        }

        return city;
    }

    @Override
    public City getById(Integer id) {
        var cityFromDb = this.cityRepository.findById(id);
        if (cityFromDb.isEmpty()) {
            throw new EntityNotFoundException("The city with the specified id was not found!");
        }
        return cityFromDb.get();
    }

    @Override
    public Integer addCity(City newCity) {
        if (newCity == null) {
            throw new InvalidDataProvidedException("City cannot be null.");
        }

        if (this.cityRepository.existsByName(newCity.getName())) {
            throw new DuplicateEntityException("City with that name already exists.");
        }

        this.cityRepository.save(newCity);
        log.info("Record for a new city has been created.");

        return newCity.getId();
    }

    @Override
    public void deleteCity(Integer id) {
        boolean cityExists = this.cityRepository.existsById(id);
        if (!cityExists) {
            throw new EntityNotFoundException("The city with the specified id was not found!");
        }

        this.cityRepository.deleteById(id);
        log.warn("City with id {} , has been deleted.", id);
    }

    @Override
    @Transactional
    public void updateCity(Integer id, CityUpdateDto updatedCity) {
        var cityFromDb = this.cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The city with the specified id was not found!"));

        if (updatedCity.getName() != null && !updatedCity.getName().isEmpty() && !updatedCity.getName().isBlank()) {
            if (this.cityRepository.existsByName(updatedCity.getName()))
                throw new DuplicateEntityException("City with that name already exists.");
            else {
                cityFromDb.setName(updatedCity.getName());
                log.info("City with id {} has been updated.", id);
            }
        } else {
            throw new InvalidDataProvidedException("Name cannot be empty.");
        }
    }
}
