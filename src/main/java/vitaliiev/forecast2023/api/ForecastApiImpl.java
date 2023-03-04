package vitaliiev.forecast2023.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vitaliiev.forecast2023.api.service.ForecastDataService;
import vitaliiev.forecast2023.api.service.LocationService;
import vitaliiev.forecast2023.api.service.LocationTimestampService;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.domain.LocationTimestampModel;
import vitaliiev.forecast2023.dto.ForecastForLocation;
import vitaliiev.forecast2023.dto.ForecastRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class ForecastApiImpl implements ForecastApiDelegate {

    private final LocationService locationService;

    private final LocationTimestampService locationTimestampService;

    private final ForecastDataService forecastDataService;


    @Autowired
    public ForecastApiImpl(LocationService locationService, LocationTimestampService locationTimestampService,
                           ForecastDataService forecastDataService) {
        this.locationService = locationService;
        this.locationTimestampService = locationTimestampService;
        this.forecastDataService = forecastDataService;
    }

    @Override
    public ResponseEntity<List<ForecastForLocation>> getForecasts(ForecastRequest forecastRequest) {
        return ResponseEntity.ok(forecastRequest.getLocations().stream()
                .filter(Objects::nonNull)
                .filter(l -> !l.isBlank())
                .map(this.locationService::findByDescription)
                .filter(Objects::nonNull)
                .map(this::getForecast)
                .collect(Collectors.toList()));
    }

    private ForecastForLocation getForecast(LocationModel locationModel) {
        return this.locationTimestampService
                .findByLocationLatestOne(locationModel)
                .filter(this.locationTimestampService::isObsolete)
                .map(this.forecastDataService::getForecastForLocationFromRepository)
                .orElseGet(() -> {
                    var locationTimestampModel = new LocationTimestampModel();
                    locationTimestampModel.setLocation(locationModel);
                    locationTimestampModel.setTimestamp(LocalDateTime.now());
                    return this.forecastDataService.getForecastForLocationFromProvider(locationTimestampModel);
                });

    }

}
