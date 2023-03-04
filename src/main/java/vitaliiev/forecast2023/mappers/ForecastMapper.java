package vitaliiev.forecast2023.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vitaliiev.forecast2023.domain.ForecastDataModel;
import vitaliiev.forecast2023.dto.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Mapper(componentModel = "spring", uses = {LocationTimestampMapper.class})
public interface ForecastMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "locationTimestamp", ignore = true)
    ForecastDataModel dtoToModel(@Valid ForecastData forecast);

    @Mapping(target = "id", ignore = true)
    ForecastDataModel dtoToModel(@Valid ForecastData forecast, @Valid LocationTimestamp locationTimestamp);

    @Named("ForecastDataModelToDto")
    ForecastData modelToDTO(@Valid ForecastDataModel forecast);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = ".", source = "forecastData")
    ForecastDataModel singlePointDtoToModel(@Valid SingleForecastDataPointForLocation forecast);

    @Mapping(source = ".", target = "forecastData", qualifiedByName = {"ForecastDataModelToDto"})
    SingleForecastDataPointForLocation modelToSinglePointDto(@Valid ForecastDataModel forecast);

    List<ForecastDataModel> singlePointDtoListToModels(List<SingleForecastDataPointForLocation> forecast);

    List<SingleForecastDataPointForLocation> modelListToSinglePointDtos(List<ForecastDataModel> forecast);

    @Mapping(source = ".", target = "forecastData")
    @Mapping(target = "locationTimestamp", ignore = true)
    SingleForecastDataPointForLocation dtoToSinglePointDto(@Valid ForecastData forecast);

    @Mapping(target = ".", source = "forecastData")
    ForecastData singlePointDtoToDto(@Valid SingleForecastDataPointForLocation forecast);

    @Mapping(target = ".", source = "main")
    @Mapping(target = "weatherDescription", source = "weather", qualifiedByName = {"WeatherOwmToString"})
    @Mapping(target = ".", source = "wind")
    ForecastData owmDataToDto(@Valid ForecastDataOWM owmData);

    @Named("WeatherOwmToString")
    default String weatherOwmToString(List<ForecastDataWeatherOWM> weatherOWM) {
        return weatherOWM.stream()
                .map(ForecastDataWeatherOWM::getWeatherDescription)
                .collect(Collectors.toList())
                .toString();
    }
}
