package vitaliiev.forecast2023.mappers;

import org.junit.jupiter.api.Test;
import vitaliiev.forecast2023.domain.LocationModel;
import vitaliiev.forecast2023.dto.Location;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Vitalii Solomonov
 * @date 02.03.2023
 */
class LocationMapperTest {

    private final static LocationMapper locationMapper = new LocationMapperImpl();

    @Test
    void testModelToDto() throws Exception {
        var name = "Moscow";
        var state = "Moscow";
        var country = "RU";
        var lat = "55.7504461";
        var lon = "37.6174943";
//        "lat": 55.7504461,
//        "lon": 37.6174943,

        var model = new LocationModel();
        model.setName(name);
        model.setCountry(country);
        model.setState(state);
        model.setLatitude(lat);
        model.setLongitude(lon);

        var dto = new Location()
                .name(name)
                .country(country)
                .state(state)
                .latitude(lat)
                .longitude(lon);

        var mappedDTO = locationMapper.modelToDTO(model);
        assertThat(dto).isEqualTo(mappedDTO);
        var mappedModel = locationMapper.dtoToModel(dto);
        assertThat(model).isEqualTo(mappedModel);
    }
}