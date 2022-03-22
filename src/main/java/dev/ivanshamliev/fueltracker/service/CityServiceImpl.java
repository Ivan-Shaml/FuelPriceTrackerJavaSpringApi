package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.CityUpdateDto;
import dev.ivanshamliev.fueltracker.model.City;
import dev.ivanshamliev.fueltracker.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.zip.DataFormatException;

@Service @Transactional @RequiredArgsConstructor
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return this.cityRepository.findAll();
    }

    @Override
    public City getByName(String name) {
        return this.cityRepository.getByName(name);
    }

    @Override
    public City getById(Integer id) throws InvalidParameterException {
        var cityFromDb = this.cityRepository.findById(id);
        if (cityFromDb.isEmpty()) {
            throw new InvalidParameterException("The city with the specified id was not found!");
        }
        return cityFromDb.get();
    }

    @Override
    public void addCity(City newCity) throws DataFormatException {
        if(newCity != null) {
            if(this.cityRepository.existsByName(newCity.getName())) {
                throw new DataFormatException("City with that name already exists.");
            }

            this.cityRepository.save(newCity);
        }
    }

    @Override
    public void deleteCity(Integer id) throws InvalidParameterException {
        boolean cityExists = this.cityRepository.existsById(id);
        if (!cityExists) {
            throw new InvalidParameterException("The city with the specified id was not found!");
        }

        this.cityRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCity(Integer id, CityUpdateDto updatedCity) throws DataFormatException {
        var cityFromDb = this.cityRepository.findById(id)
                .orElseThrow(() -> new InvalidParameterException("The city with the specified id was not found!"));
        if (updatedCity.getName() != null && !updatedCity.getName().isEmpty() && !updatedCity.getName().isBlank()) {
            if(this.cityRepository.existsByName(updatedCity.getName()))
                throw new DataFormatException("City with that name already exists.");
            else{
                cityFromDb.setName(updatedCity.getName());
            }
        }else {
            throw new DataFormatException("Name cannot be empty.");
        }
    }
}
