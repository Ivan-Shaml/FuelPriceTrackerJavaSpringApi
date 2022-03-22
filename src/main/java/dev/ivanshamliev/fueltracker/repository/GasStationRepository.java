package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Integer> {
    Optional<List<GasStation>> getAllByLocationName(String locationName);
}
