package vitaliiev.forecast2023.openweathermap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vitaliiev.forecast2023.config.Forecast2023Properties;
import vitaliiev.forecast2023.dto.*;
import vitaliiev.forecast2023.exceptions.ForecastProviderException;
import vitaliiev.forecast2023.mappers.ForecastMapper;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class Forecast5Day3HoursImpl implements Forecast {

    private final Forecast2023Properties properties;


    private final ForecastMapper forecastMapper;

    private final ObjectMapper objectMapper;

    @Autowired
    public Forecast5Day3HoursImpl(Forecast2023Properties properties, ForecastMapper forecastMapper,
                                  ObjectMapper objectMapper) {
        this.properties = properties;
        this.forecastMapper = forecastMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<ForecastForLocation> getForecastForLocation(@Valid Location location) throws ForecastProviderException {
        if (location == null) {
            return Optional.empty();
        }
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("appid", this.properties.getOwmApiKey());
        if (this.properties.getUnits() != null && !this.properties.getUnits().isBlank()) {
            urlParams.put("units", this.properties.getUnits());
        }
        if (this.properties.getLang() != null && !this.properties.getLang().isBlank()) {
            urlParams.put("lang", this.properties.getLang());
        }
        urlParams.put("lat", location.getLatitude());
        urlParams.put("lon", location.getLongitude());
        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://api.openweathermap.org/data/2.5/forecast" +
                        "?lat={lat}&lon={lon}&units={units}&lang={lang}&appid={appid}")
                .build(urlParams);
        return Optional.of(new RestTemplate().getForEntity(uri, String.class))
                .map(response -> this.mapToForecast(response, location));
    }

    private ForecastForLocation mapToForecast(ResponseEntity<String> response, Location request) throws ForecastProviderException {

        try {
            if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
                ForecastForLocationOWM forecastOWM = this.objectMapper.readValue(response.getBody(),
                        ForecastForLocationOWM.class);
                List<ForecastData> forecastDataList = forecastOWM
                        .getForecastData().stream()
                        .map(this.forecastMapper::owmDataToDto)
                        .collect(Collectors.toList());
                return new ForecastForLocation()
                        .locationTimestamp(new LocationTimestamp()
                                .location(request)
                                .timestamp(LocalDateTime.now()))
                        .forecastData(this.properties.getForecastDataReducer().apply(forecastDataList));

            } else {
                var message = String.format("Cannot get forecast from external provider. Response code: %d. Body: %s",
                        response.getStatusCode().value(), response.getBody());
                throw new ForecastProviderException(message);
            }
        } catch (JsonProcessingException e) {
            throw new ForecastProviderException(e);
        }

    }

}
