package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
