package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    @Query("select f from Fuel f where f.gasStation.id = ?1")
    Optional<List<Fuel>> getAllByGasStationId(Integer id);
}
