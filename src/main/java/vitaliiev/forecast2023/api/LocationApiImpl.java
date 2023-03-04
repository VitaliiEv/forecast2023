package vitaliiev.forecast2023.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vitaliiev.forecast2023.api.service.LocationService;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.mappers.LocationMapper;

import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Service
public class LocationApiImpl implements LocationApiDelegate {

    private final LocationMapper locationMapper;

    private final LocationService locations;

    @Autowired
    public LocationApiImpl(LocationMapper locationMapper, LocationService locations) {
        this.locationMapper = locationMapper;
        this.locations = locations;
    }

    @Override
    public ResponseEntity<List<Location>> getLocations() {
        return ResponseEntity.ok(this.locationMapper.locationsModelsToLocations(this.locations.findAll()));
    }
}
