package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.zip.DataFormatException;

public interface CityService {
    List<City> getAllCities();
    City getByName(String name);
    City getById(Integer id) throws InvalidParameterException;
    void addCity(City newCity) throws DataFormatException;
    void deleteCity(Integer id) throws InvalidParameterException;
    void updateCity(Integer id, CityUpdateDto updatedCity) throws DataFormatException;
}
