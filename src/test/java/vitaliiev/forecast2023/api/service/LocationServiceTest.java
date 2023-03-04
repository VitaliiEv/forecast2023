package vitaliiev.forecast2023.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.Forecast2023ApplicationTests;
import vitaliiev.forecast2023.mappers.LocationMapper;
import vitaliiev.forecast2023.repository.LocationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vitalii Solomonov
 * @date 03.03.2023
 */
class LocationServiceTest extends Forecast2023ApplicationTests {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private LocationMapper mapper;

    @Autowired
    private LocationService locationService;

    @BeforeEach
    @AfterEach
    void beforeAndAfterEach() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscow());
        this.repository.delete(model);
    }

    @Test
    @Transactional
    void testCreate() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscow());
        var savedModel = this.locationService.create(model);
        var receivedModel = this.repository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }

    @Test
    @Transactional
    void testCreateDuplicate() {
        var model = this.mapper.dtoToModel(TestDTOs.getMoscow());
        var savedModel = this.locationService.create(model);
        assertThrows(ResponseStatusException.class, () -> this.locationService.create(model));
        var receivedModel = this.repository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }
}