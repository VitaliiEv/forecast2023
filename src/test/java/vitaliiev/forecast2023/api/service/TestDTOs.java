package vitaliiev.forecast2023.api.service;

import vitaliiev.forecast2023.dto.ForecastData;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.dto.LocationTimestamp;

import java.time.LocalDateTime;

/**
 * @author Vitalii Solomonov
 * @date 04.03.2023
 */
public class TestDTOs {

    static Location getMoscow() {
        return new Location()
                .name("Moscow")
                .country("RU")
                .state("Moscow")
                .latitude("55.7504461")
                .longitude("37.6174943");
    }

    static LocationTimestamp getMoscow2000() {
        return new LocationTimestamp()
                .location(getMoscow())
                .timestamp(LocalDateTime.of(2000, 1, 1, 0, 0, 0));
    }

    static ForecastData getMoscowForecastData() {
        return new ForecastData()
                .datetime(getMoscow2000().getTimestamp())
                .humidity(100)
                .pressure(1000.0)
                .temperature(+4.0)
                .temperatureFeelsLike(-1.0)
                .weatherDescription("ds")
                .windDirection("North")
                .windSpeed(10.0);
    }

}
