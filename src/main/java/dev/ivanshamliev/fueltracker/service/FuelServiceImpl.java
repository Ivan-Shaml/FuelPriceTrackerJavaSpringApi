package dev.ivanshamliev.fueltracker.service;

import dev.ivanshamliev.fueltracker.dto.FuelCreateDto;
import dev.ivanshamliev.fueltracker.dto.FuelReadDto;
import dev.ivanshamliev.fueltracker.dto.FuelUpdateDto;
import dev.ivanshamliev.fueltracker.dto.PriceHistoryReadDto;
import dev.ivanshamliev.fueltracker.exception.EntityNotFoundException;
import dev.ivanshamliev.fueltracker.exception.InvalidDataProvidedException;
import dev.ivanshamliev.fueltracker.model.Fuel;
import dev.ivanshamliev.fueltracker.model.PriceHistory;
import dev.ivanshamliev.fueltracker.repository.FuelRepository;
import dev.ivanshamliev.fueltracker.repository.GasStationRepository;
import dev.ivanshamliev.fueltracker.repository.PriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuelServiceImpl implements FuelService {

    private final GasStationRepository gasStationRepository;
    private final FuelRepository fuelRepository;
    private final PriceHistoryRepository priceRepository;


    @Override
    public List<Fuel> getAll() {
        return fuelRepository.findAll();
    }

    @Override
    public Fuel getById(Integer id) {
        var fuel = fuelRepository.findById(id);
        if (fuel.isEmpty()) {
            throw new EntityNotFoundException("Fuel with the specified id does not exist.");
        }

        return fuel.get();
    }

    @Override
    public Integer addFuel(FuelCreateDto fuelCreateDto) {

        if (fuelCreateDto.getName().isEmpty() || fuelCreateDto.getName().isBlank()) {
            throw new InvalidDataProvidedException("The fuel's name cannot be null or empty.");
        }
        if (fuelCreateDto.getPricePerLiter() <= 0) {
            throw new InvalidDataProvidedException("The fuel's price cannot be negative or zero.");
        }
        var gasStationFromDb = this.gasStationRepository.findById(fuelCreateDto.getGasStationId());
        if (gasStationFromDb.isEmpty()) {
            throw new InvalidDataProvidedException("The specified gas station doesn't exist.");
        }

        Fuel fuel = new Fuel(fuelCreateDto.getName(),
                fuelCreateDto.getPricePerLiter(),
                LocalDateTime.now(),
                gasStationFromDb.get()
        );

        this.fuelRepository.save(fuel);
        log.info("Record for a new fuel has been created.");

        return fuel.getId();
    }

    @Override
    public void deleteFuel(Integer id) {
        boolean fuelExists = this.fuelRepository.existsById(id);

        if (!fuelExists) {
            throw new EntityNotFoundException("The fuel with the specified id was not found!");
        }

        this.fuelRepository.deleteById(id);
        log.warn("Fuel with id {} has been deleted.", id);
    }

    @Override
    @Transactional
    public void updatePrice(Integer fuelId, Double newPrice) {
        var fuelFromDb = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new EntityNotFoundException("Fuel with the specified id does not exist."));

        Double oldPrice = fuelFromDb.getPricePerLiter();

        if (!oldPrice.equals(newPrice)) {
            PriceHistory prHist = new PriceHistory(
                    LocalDateTime.now(),
                    oldPrice,
                    newPrice,
                    fuelFromDb
            );

            this.priceRepository.save(prHist);
            fuelFromDb.setPricePerLiter(newPrice);
            fuelFromDb.setLastUpdate(LocalDateTime.now());
            log.warn("Price has been updated to {} , from {} , for fuel with id {}.", oldPrice, newPrice, fuelId);
        }
    }

    @Override
    @Transactional
    public void updateFuel(Integer id, FuelUpdateDto fuelUpdateDto) {

        var fuelFromDb = this.fuelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fuel with the specified id does not exist."));
        var gasStationFromDb = this.gasStationRepository.findById(fuelUpdateDto.getGasStationId())
                .orElseThrow(() -> new InvalidDataProvidedException("Gas station with the specified id does not exist."));

        if (fuelUpdateDto.getName().isEmpty() || fuelUpdateDto.getName().isBlank()) {
            throw new InvalidDataProvidedException("The fuel's name cannot be null or empty.");
        }

        if (!fuelFromDb.getPricePerLiter().equals(fuelUpdateDto.getPricePerLiter())) {
            this.updatePrice(id, fuelUpdateDto.getPricePerLiter());
        }
        fuelFromDb.setName(fuelUpdateDto.getName());
        fuelFromDb.setGasStation(gasStationFromDb);
        fuelFromDb.setLastUpdate(LocalDateTime.now());

        log.info("Fuel with id {} has been updated.", id);
    }

    private PriceHistoryReadDto mapToPriceHistoryReadDto(PriceHistory prH) {
        return new PriceHistoryReadDto(
                prH.getId(),
                prH.getTimeUpdated(),
                prH.getOldPrice(),
                prH.getNewPrice()
        );
    }

    @Override
    public FuelReadDto mapToReadDto(Integer id) {
        var fuelFromDb = this.fuelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fuel with the specified id does not exist."));

        var history = this.priceRepository.getAllByFuelId(fuelFromDb.getId());

        if (history.isPresent()) {
            List<PriceHistoryReadDto> priceHistoryList = new ArrayList<>();

            history.get().forEach(priceHistory -> {
                priceHistoryList.add(this.mapToPriceHistoryReadDto(priceHistory));
            });

            return new FuelReadDto(
                    fuelFromDb.getId(),
                    fuelFromDb.getName(),
                    fuelFromDb.getPricePerLiter(),
                    fuelFromDb.getLastUpdate(),
                    fuelFromDb.getGasStation(),
                    priceHistoryList
            );
        }

        return new FuelReadDto(
                fuelFromDb.getId(),
                fuelFromDb.getName(),
                fuelFromDb.getPricePerLiter(),
                fuelFromDb.getLastUpdate(),
                fuelFromDb.getGasStation(),
                null
        );
    }

    @Override
    public FuelReadDto getForDateToReadDto(Integer id, String date) {
        var fuelFromDb = this.fuelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fuel with the specified id does not exist."));

        // check if provided the date is in the correct format, it doesn't match, an exception will be thrown
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(date.toLowerCase(), formatter);

        var history = this.priceRepository.getAllByFuelId(fuelFromDb.getId());

        if (history.isPresent()) {
            List<PriceHistoryReadDto> priceHistoryList = new ArrayList<>();

            history.get()
                    .stream().filter(price -> price.getTimeUpdated().toLocalDate().equals(targetDate))
                    .forEach(priceHistory -> {
                        priceHistoryList.add(this.mapToPriceHistoryReadDto(priceHistory));
                    });

            return new FuelReadDto(
                    fuelFromDb.getId(),
                    fuelFromDb.getName(),
                    fuelFromDb.getPricePerLiter(),
                    fuelFromDb.getLastUpdate(),
                    fuelFromDb.getGasStation(),
                    priceHistoryList
            );
        }

        return new FuelReadDto(
                fuelFromDb.getId(),
                fuelFromDb.getName(),
                fuelFromDb.getPricePerLiter(),
                fuelFromDb.getLastUpdate(),
                fuelFromDb.getGasStation(),
                new ArrayList<>()
        );
    }
}
