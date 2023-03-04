package vitaliiev.forecast2023;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.domain.LocationTimestampModel;
import vitaliiev.forecast2023.dto.ForecastData;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.mappers.ForecastMapper;
import vitaliiev.forecast2023.mappers.LocationMapper;
import vitaliiev.forecast2023.mappers.LocationTimestampMapper;
import vitaliiev.forecast2023.repository.ForecastDataRepository;
import vitaliiev.forecast2023.repository.LocationRepository;
import vitaliiev.forecast2023.repository.LocationTimestampRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitalii Solomonov
 * @date 04.03.2023
 */
@TestComponent
public class TestData implements CommandLineRunner {

    private static final int LOCATIONS_TOTAL = 10;

    private static final int TIMESTAMPS_PER_LOCATION = 3;

    private static final int FORECAST_DATA_DER_TIMESTAMP = 5;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTimestampRepository locationTimestampRepository;

    @Autowired
    private ForecastDataRepository forecastDataRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ForecastMapper forecastMapper;

    @Autowired
    private LocationTimestampMapper locationTimestampMapper;

    private final Map<Long, LocationModel> locations = new HashMap<>();


    @Override
    @Transactional
    public void run(String... args) {
        for (int i = 0; i < LOCATIONS_TOTAL; i++) {
            var location = new Location()
                    .name("Location " + i)
                    .country(Integer.toString(i))
                    .state("Location " + i)
                    .latitude("55.04461" + i)
                    .longitude("37.61749" + i);
            var locationModel = this.locationRepository.save(this.locationMapper.dtoToModel(location));
            for (int j = 0; j < TIMESTAMPS_PER_LOCATION; j++) {
                var locationTimestampModel = new LocationTimestampModel();
                locationTimestampModel.setLocation(locationModel);
                locationTimestampModel.setTimestamp(LocalDateTime.now().minusDays(i + 1).minusHours(j + 1));
                locationTimestampModel = this.locationTimestampRepository.save(locationTimestampModel);
                for (int k = 0; k < FORECAST_DATA_DER_TIMESTAMP; k++) {
                    var forecastData = new ForecastData()
                            .datetime(locationTimestampModel.getTimestamp().plusDays(k))
                            .humidity(30 + k * 5)
                            .pressure(1000.0 + 10 * k)
                            .temperature(+4.0 + k)
                            .temperatureFeelsLike(-1.0 + k)
                            .weatherDescription("descr" + k)
                            .windDirection("North")
                            .windSpeed(10.0 + k);
                    var forecastDataModel = this.forecastMapper.dtoToModel(forecastData,
                            this.locationTimestampMapper.modelToDTO(locationTimestampModel));
                    forecastDataModel.setLocationTimestamp(locationTimestampModel);
                    this.forecastDataRepository.save(forecastDataModel);

                }
            }
        }
    }
}
