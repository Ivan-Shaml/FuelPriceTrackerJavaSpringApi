package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    Optional<List<Fuel>> getAllByGasStationId(Integer id);
}
