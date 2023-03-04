package vitaliiev.forecast2023.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vitaliiev.forecast2023.dto.ForecastForLocation;
import vitaliiev.forecast2023.dto.ForecastRequest;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.mappers.LocationMapper;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class ForecastService {

    private final LocationMapper locationMapper;

    @Autowired
    public ForecastService(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    @Value("${forecast2023.rest-api-url}")
    private String apiUrl;

    private static final String LOCATION_URL_SUFFIX = "/location";

    private static final String FORECAST_URL_SUFFIX = "/forecast";

    public List<ForecastForLocation> getForecast(ForecastRequest forecastRequest) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(this.apiUrl + FORECAST_URL_SUFFIX)
                .build(new HashMap<>());
        ResponseEntity<ForecastForLocation[]> response =
                new RestTemplate().postForEntity(uri, forecastRequest, ForecastForLocation[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return Arrays.stream(response.getBody()).toList();
        }
        return new ArrayList<>();
    }

    public List<Location> findAll() {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(this.apiUrl + LOCATION_URL_SUFFIX)
                .build(new HashMap<>());
        ResponseEntity<Location[]> response = new RestTemplate().getForEntity(uri, Location[].class);
        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            return Arrays.stream(response.getBody()).toList();
        }
        return new ArrayList<>();
    }

    public List<String> findAllAsString() {
        return this.findAll().stream()
                .map(this.locationMapper::locationToOwmDescriptionString)
                .collect(Collectors.toList());
    }

}
