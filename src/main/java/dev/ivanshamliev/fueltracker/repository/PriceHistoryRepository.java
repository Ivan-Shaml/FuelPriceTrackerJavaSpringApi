package dev.ivanshamliev.fueltracker.repository;

import dev.ivanshamliev.fueltracker.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
    Optional<List<PriceHistory>> getAllByFuelId(Integer id);
    void deleteAllByFuelId(Integer id);
}
