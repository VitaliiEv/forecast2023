package vitaliiev.forecast2023.openweathermap.reducers;

import vitaliiev.forecast2023.dto.ForecastData;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 28.02.2023
 */
public class DefaultForecastDataReducer implements ForecastDataReducer {
    @Override
    public List<ForecastData> apply(List<ForecastData> forecastData) {
        var time = forecastData.stream()
                .map(ForecastData::getDatetime)
                .min(Comparator.naturalOrder())
                .map(LocalDateTime::toLocalTime)
                .orElseThrow();
        return forecastData.stream()
                .filter(f -> f.getDatetime().toLocalTime().equals(time))
                .collect(Collectors.toList());
    }
}
