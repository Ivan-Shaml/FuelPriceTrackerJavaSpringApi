package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.GasStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GasStationRepository extends JpaRepository<GasStation, Integer> {
}
