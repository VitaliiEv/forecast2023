package vitaliiev.forecast2023.api.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.exceptions.GeolocationProviderException;
import vitaliiev.forecast2023.exceptions.LocationNotFound;
import vitaliiev.forecast2023.mappers.LocationMapper;
import vitaliiev.forecast2023.openweathermap.Geocode;
import vitaliiev.forecast2023.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class LocationService {

    private final LocationRepository repository;

    private final LocationMapper mapper;

    private final Geocode geocodeProvider;

    @Autowired
    public LocationService(LocationRepository repository, LocationMapper mapper, Geocode geocodeProvider) {
        this.repository = repository;
        this.mapper = mapper;
        this.geocodeProvider = geocodeProvider;
    }

    @Transactional
    public LocationModel create(@Valid LocationModel model) {
        if (this.repository.exists(Example.of(model))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Location '%s' already exists", this.mapper.modelToDTO(model).toString())
            );
        }
        return this.repository.saveAndFlush(model);
    }

    @Transactional(readOnly = true)
    public Optional<LocationModel> findOne(LocationModel model) {
        return this.repository.findOne(Example.of(model));
    }

    @Transactional(readOnly = true)
    public List<LocationModel> findAll() {
        return this.repository.findAll();
    }

    @Transactional
    public LocationModel findByDescription(String location) {
        try {
            return this.geocodeProvider.getLocation(location)
                    .map(this.mapper::dtoToModel)
                    .map(l -> this.findOne(l)
                            .orElseGet(() -> this.create(l)))
                    .orElseThrow(() -> new LocationNotFound(location));
        } catch (GeolocationProviderException e) {
            return null;
        }
    }
}
