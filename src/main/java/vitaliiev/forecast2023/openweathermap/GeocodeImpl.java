package vitaliiev.forecast2023.openweathermap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vitaliiev.forecast2023.config.Forecast2023Properties;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.dto.LocationOWM;
import vitaliiev.forecast2023.exceptions.GeolocationProviderException;
import vitaliiev.forecast2023.mappers.LocationMapper;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class GeocodeImpl implements Geocode {

    private final Forecast2023Properties properties;

    private final LocationMapper locationMapper;

    private final ObjectMapper objectMapper;

    @Autowired
    public GeocodeImpl(Forecast2023Properties properties, LocationMapper locationMapper, ObjectMapper objectMapper) {
        this.properties = properties;
        this.locationMapper = locationMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Location> getLocation(String location) throws GeolocationProviderException {
        if (location == null || location.isBlank()) {
            return Optional.empty();
        }
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("appid", this.properties.getOwmApiKey());
        urlParams.put("location", location);
        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://api.openweathermap.org/geo/1.0/direct?q={location}&appid={appid}")
                .build(urlParams);
        return Optional.of(new RestTemplate().getForEntity(uri, String.class))
                .map(response -> this.mapToLocation(response, location));
    }

    private Location mapToLocation(ResponseEntity<String> response, String request) throws GeolocationProviderException {
        try {
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                LocationOWM[] result = this.objectMapper.readValue(response.getBody(), LocationOWM[].class);
                if (result.length == 1) {
                    return this.locationMapper.owmToDTO(result[0]);
                } else if (result.length == 0) {
                    var message = String.format("Location '%s' not found", request);
                    throw new GeolocationProviderException(message);
                } else {
                    var message = String.format("Expected one result, got %d for location '%s'. Results: %s",
                            result.length,
                            request,
                            Arrays.stream(result)
                                    .map(LocationOWM::getName)
                                    .collect(Collectors.toList()));
                    throw new GeolocationProviderException(message);
                }
            } else {
                var message = String.format("Cannot get location from external provider. Response code: %d. Body: %s",
                        response.getStatusCode().value(), response.getBody());
                throw new GeolocationProviderException(message);
            }
        } catch (JsonProcessingException e) {
            throw new GeolocationProviderException(e);
        }
    }
}
