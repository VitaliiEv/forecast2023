package vitaliiev.forecast2023.openweathermap.reducers;

import vitaliiev.forecast2023.dto.ForecastData;

import java.util.List;
import java.util.function.Function;

/**
 * @author Vitalii Solomonov
 * @date 28.02.2023
 */
@FunctionalInterface
public interface ForecastDataReducer extends Function<List<ForecastData>, List<ForecastData>> {
}
