package vitaliiev.forecast2023.openweathermap;

import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.exceptions.GeolocationProviderException;

import java.util.Optional;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
public interface Geocode {

    Optional<Location> getLocation(String location) throws GeolocationProviderException;

}
