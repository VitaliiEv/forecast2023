package vitaliiev.forecast2023.api.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.domain.LocationTimestampModel;
import vitaliiev.forecast2023.exceptions.LocationNotFound;
import vitaliiev.forecast2023.mappers.LocationTimestampMapper;
import vitaliiev.forecast2023.repository.LocationRepository;
import vitaliiev.forecast2023.repository.LocationTimestampRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class LocationTimestampService {

    private final LocationTimestampRepository repository;

    private final LocationRepository locationRepository;

    private final LocationTimestampMapper locationTimestampMapper;

    @Value("${forecast2023.obsolete}")
    private Duration obsoletePeriod;

    @Autowired
    public LocationTimestampService(LocationTimestampRepository repository, LocationRepository locationRepository,
                                    LocationTimestampMapper locationTimestampMapper) {
        this.repository = repository;
        this.locationRepository = locationRepository;
        this.locationTimestampMapper = locationTimestampMapper;
    }

    @Transactional
    public LocationTimestampModel create(@Valid LocationTimestampModel model) {
        if (!this.locationRepository.exists(Example.of(model.getLocation()))) {
            throw new LocationNotFound(this.locationTimestampMapper.modelToDTO(model).getLocation().toString());
        }
        if (this.repository.exists(Example.of(model))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Timestamped location '%s' already exists", this.locationTimestampMapper
                            .modelToDTO(model)
                            .getLocation()
                            .toString()));
        }
        return this.repository.saveAndFlush(model);
    }

    @Transactional(readOnly = true)
    public Optional<LocationTimestampModel> findByLocationLatestOne(LocationModel location) {
        return this.findAllByLocation(location).stream()
                .max(Comparator.comparing(LocationTimestampModel::getTimestamp));
    }

    @Transactional(readOnly = true)
    public List<LocationTimestampModel> findAllByLocation(LocationModel location) {
        LocationModel retrievedLocation = this.locationRepository.findOne(Example.of(location))
                .orElseThrow(() -> new LocationNotFound(location.toString()));
        var example = new LocationTimestampModel();
        example.setLocation(retrievedLocation);
        return this.repository.findAll(Example.of(example));
    }

    public boolean isObsolete(LocationTimestampModel locationTimestamp) {
        return locationTimestamp
                .getTimestamp()
                .isAfter(LocalDateTime.now().minus(this.obsoletePeriod));
    }
}
