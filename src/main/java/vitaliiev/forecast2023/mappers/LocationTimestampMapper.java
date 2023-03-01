package vitaliiev.forecast2023.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vitaliiev.forecast2023.domain.LocationTimestampModel;
import vitaliiev.forecast2023.dto.LocationTimestamp;

import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface LocationTimestampMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "forecastData", ignore = true)
    LocationTimestampModel dtoToModel(@Valid LocationTimestamp dto);

    List<LocationTimestampModel> dtoListToModelList(List<LocationTimestamp> dtoList);

    LocationTimestamp modelToDTO(@Valid LocationTimestampModel model);

    List<LocationTimestamp> modelListToDTOList(List<LocationTimestampModel> modelList);

}
