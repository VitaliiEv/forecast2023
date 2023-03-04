package vitaliiev.forecast2023.openweathermap.reducers;

import vitaliiev.forecast2023.dto.ForecastData;

import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 28.02.2023
 */
public class NoopForecastDataReducer implements ForecastDataReducer {
    @Override
    public List<ForecastData> apply(List<ForecastData> forecastData) {
        return forecastData;
    }
}
