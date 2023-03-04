package vitaliiev.forecast2023.openweathermap;

import vitaliiev.forecast2023.dto.ForecastForLocation;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.exceptions.ForecastProviderException;

import java.util.Optional;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
public interface Forecast {

    Optional<ForecastForLocation> getForecastForLocation(Location location) throws ForecastProviderException;


}
