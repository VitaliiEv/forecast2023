package vitaliiev.forecast2023.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.dto.LocationOWM;

import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(target = "locationTimestamps", ignore = true)
    @Mapping(target = "id", ignore = true)
    LocationModel dtoToModel(Location location);

    List<LocationModel> locationsToLocationModels(List<Location> locations);

    Location modelToDTO(LocationModel location);

    List<Location> locationsModelsToLocations(List<LocationModel> locations);

    Location owmToDTO(LocationOWM location);
}
