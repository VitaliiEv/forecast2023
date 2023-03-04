package vitaliiev.forecast2023.api.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.domain.ForecastDataModel;
import vitaliiev.forecast2023.domain.LocationTimestampModel;
import vitaliiev.forecast2023.dto.ForecastForLocation;
import vitaliiev.forecast2023.exceptions.ForecastProviderException;
import vitaliiev.forecast2023.exceptions.LocationTimestampNotFound;
import vitaliiev.forecast2023.mappers.ForecastMapper;
import vitaliiev.forecast2023.mappers.LocationTimestampMapper;
import vitaliiev.forecast2023.openweathermap.Forecast;
import vitaliiev.forecast2023.repository.ForecastDataRepository;
import vitaliiev.forecast2023.repository.LocationTimestampRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 27.02.2023
 */
@Service
public class ForecastDataService {

    private final ForecastDataRepository repository;

    private final LocationTimestampRepository locationTimestampRepository;

    private final ForecastMapper forecastMapper;

    @Autowired
    private LocationTimestampMapper locationTimestampMapper;


    private final Forecast forecastProvider;

    private final LocationTimestampService locationTimestampService;

    @Autowired
    public ForecastDataService(ForecastDataRepository repository,
                               LocationTimestampRepository locationTimestampRepository, ForecastMapper forecastMapper,
                               Forecast forecastProvider, LocationTimestampService locationTimestampService) {
        this.repository = repository;
        this.locationTimestampRepository = locationTimestampRepository;
        this.forecastMapper = forecastMapper;
        this.forecastProvider = forecastProvider;
        this.locationTimestampService = locationTimestampService;
    }

    @Transactional
    public ForecastDataModel create(@Valid ForecastDataModel model) {
        this.locationTimestampRepository.findById(model.getLocationTimestamp().getId())
                .orElseThrow(() -> new LocationTimestampNotFound(this.locationTimestampMapper
                        .modelToDTO(model
                                .getLocationTimestamp())
                        .getLocation()
                        .toString()));
        if (this.repository.exists(Example.of(model))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Forecast '%s' already exists", model));
        }
        return this.repository.saveAndFlush(model);
    }

    @Transactional(readOnly = true)
    public List<ForecastDataModel> findAllByLocationTimestamp(@Valid LocationTimestampModel locationTimestampModel) {
        var example = new ForecastDataModel();
        example.setLocationTimestamp(locationTimestampModel);
        return this.repository.findAll(Example.of(example));
    }

    @Transactional(readOnly = true)
    public ForecastForLocation getForecastForLocationFromRepository(LocationTimestampModel locationTimestampModel) {
        var forecastData = this.findAllByLocationTimestamp(locationTimestampModel).stream()
                .map(this.forecastMapper::modelToDTO)
                .collect(Collectors.toList());
        var location = this.locationTimestampMapper.modelToDTO(locationTimestampModel);
        return new ForecastForLocation()
                .locationTimestamp(location)
                .forecastData(forecastData);
    }

    @Transactional
    public ForecastForLocation getForecastForLocationFromProvider(LocationTimestampModel locationTimestampModel) {
        try {
            var forecastForLocation = this.forecastProvider
                    .getForecastForLocation(this.locationTimestampMapper
                            .modelToDTO(locationTimestampModel)
                            .getLocation())
                    .orElseThrow();
            var storedLocationTimestamp = this.locationTimestampService.create(locationTimestampModel);
            var forecastData = forecastForLocation.getForecastData().stream()
                    .map(this.forecastMapper::dtoToModel)
                    .map(forecastDataModel -> {
                        forecastDataModel.setLocationTimestamp(storedLocationTimestamp);
                        return forecastDataModel;
                    })
                    .map(this::create)
                    .map(this.forecastMapper::modelToDTO)
                    .collect(Collectors.toList());
            return new ForecastForLocation()
                    .locationTimestamp(this.locationTimestampMapper.modelToDTO(storedLocationTimestamp))
                    .forecastData(forecastData);
        } catch (ForecastProviderException e) {
            return null;
        }

    }
}
