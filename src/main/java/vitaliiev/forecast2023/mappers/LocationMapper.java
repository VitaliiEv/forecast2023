package vitaliiev.forecast2023.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.dto.LocationOWM;

import java.util.Arrays;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "locationTimestamps", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", numberFormat = "##.#######")
    @Mapping(target = "longitude", numberFormat = "###.#######")
    LocationModel dtoToModel(@Valid Location location);

    List<LocationModel> locationsToLocationModels(List<Location> locations);

    Location modelToDTO(@Valid LocationModel location);

    List<Location> locationsModelsToLocations(List<LocationModel> locations);

    Location owmToDTO(@Valid LocationOWM location);

    default Location mapOwmDescriptionToLocation(String location) {
        var locationStrings = Arrays.stream(location.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        switch (locationStrings.length) {
            case 1:
                return new Location()
                        .name(locationStrings[0]);
            case 2:
                return new Location()
                        .name(locationStrings[0])
                        .country(locationStrings[1]);
            case 3:
                return new Location()
                        .name(locationStrings[0])
                        .country(locationStrings[2])
                        .state(locationStrings[1]);
            default:
                return new Location()
                        .name(location);
        }
    }

    default String locationToOwmDescriptionString(@Valid Location location) {
        var str = location.getName();
        if (location.getState() != null && !location.getState().isEmpty()) {
            str = str + ", " + location.getState();
        }
        if (location.getCountry() != null && !location.getCountry().isEmpty()) {
            str = str + ", " + location.getCountry();
        }
        return str;
    }
}
