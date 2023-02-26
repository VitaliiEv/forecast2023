package vitaliiev.forecast2023.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.exceptions.GeolocationProviderException;
import vitaliiev.forecast2023.mappers.LocationMapper;
import vitaliiev.forecast2023.openweathermap.Geocode;
import vitaliiev.forecast2023.repository.LocationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class LocationsService {

    @Autowired
    private LocationRepository locations;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private Geocode geocode;

    @Transactional
    public List<Location> findAllByDescription(List<String> locations) {
        return locations.stream()
                .map(this::findByDescription)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public Location findByDescription(String location) {
        var example = locationMapper.dtoToModel(mapToLocation(location));
        var modelOptional = this.locations.findOne(Example.of(example));
        try {
            var model = modelOptional
                    .orElse(this.geocode.getLocation(location)
                            .map(this.locationMapper::dtoToModel)
                            .map(this.locations::save)
                            .orElse(null));
            return this.locationMapper.modelToDTO(model);
        } catch (GeolocationProviderException e) {
            // todo add message model view
            return null;
        }
    }

    @Transactional
    public List<Location> findAll() {
        return this.locationMapper.locationsModelsToLocations(this.locations.findAll());
    }

    @Transactional
    public List<String> findAllAsString() {
        return this.findAll().stream()
                .map(this::locationToOwmString)
                .collect(Collectors.toList());
    }

    private Location mapToLocation(String location) {
        var locationStrings = Arrays.stream(location.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        switch (locationStrings.length) {
            case 1:
                return new Location()
                        .name(locationStrings[0]);
            case 2:
                return new Location()
                        .name(locationStrings[0])
                        .country(locationStrings[1]);
            case 3:
                return new Location()
                        .name(locationStrings[0])
                        .country(locationStrings[2])
                        .state(locationStrings[1]);
            default:
                return new Location()
                        .name(location);
        }
    }

    private String locationToOwmString(@Valid Location location) {
        var str = location.getName();
        if (!location.getState().isEmpty()) {
            str = str + ", " + location.getState();
        }
        if (!location.getCountry().isEmpty()) {
            str = str + ", " + location.getCountry();
        }
        return str;
    }

}
