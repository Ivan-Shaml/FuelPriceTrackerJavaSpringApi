package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
}
