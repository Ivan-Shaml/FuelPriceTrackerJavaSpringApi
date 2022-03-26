package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;
import org.springframework.dao.DataIntegrityViolationException;

import java.security.InvalidParameterException;
import java.util.List;

public interface CityService {
    List<City> getAllCities();

    City getByName(String name);

    City getById(Integer id);

    Integer addCity(City newCity);

    void deleteCity(Integer id);

    void updateCity(Integer id, CityUpdateDto updatedCity);
}
