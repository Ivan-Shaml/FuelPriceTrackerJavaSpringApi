package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;
import org.springframework.dao.DataIntegrityViolationException;
import java.security.InvalidParameterException;
import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City getByName(String name);
    City getById(Integer id) throws InvalidParameterException;
    Integer addCity(City newCity) throws DataIntegrityViolationException;
    void deleteCity(Integer id) throws InvalidParameterException;
    void updateCity(Integer id, CityUpdateDto updatedCity) throws DataIntegrityViolationException;
}
