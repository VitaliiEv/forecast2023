package vitaliiev.forecast2023.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.Forecast2023ApplicationTests;
import vitaliiev.forecast2023.mappers.ForecastMapper;
import vitaliiev.forecast2023.repository.ForecastDataRepository;
import vitaliiev.forecast2023.repository.LocationTimestampRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vitalii Solomonov
 * @date 04.03.2023
 */
class ForecastDataServiceTest extends Forecast2023ApplicationTests {

    @Autowired
    private ForecastDataRepository forecastDataRepository;

    @Autowired
    private LocationTimestampRepository locationTimestampRepository;

    @Autowired
    private ForecastMapper mapper;

    @Autowired
    private ForecastDataService forecastDataService;


    @BeforeEach
    @AfterEach
    void beforeAndAfterEach() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscowForecastData(), TestDTOs.getMoscow2000());
        this.forecastDataRepository.delete(model);
    }

    @Test
    @Transactional
    void testCreate() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscowForecastData(), TestDTOs.getMoscow2000());
        model.setLocationTimestamp(this.locationTimestampRepository.findAll().stream().findAny().orElseThrow());
        var savedModel = this.forecastDataService.create(model);
        var receivedModel = this.forecastDataRepository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }

    @Test
    @Transactional
    void testCreateDuplicate() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscowForecastData(), TestDTOs.getMoscow2000());
        model.setLocationTimestamp(this.locationTimestampRepository.findAll().stream().findAny().orElseThrow());
        var savedModel = this.forecastDataService.create(model);
        assertThrows(ResponseStatusException.class, () -> this.forecastDataService.create(model));
        var receivedModel = this.forecastDataRepository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }
}