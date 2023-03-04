package vitaliiev.forecast2023.api.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vitaliiev.forecast2023.Forecast2023ApplicationTests;
import vitaliiev.forecast2023.mappers.LocationTimestampMapper;
import vitaliiev.forecast2023.repository.LocationRepository;
import vitaliiev.forecast2023.repository.LocationTimestampRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Vitalii Solomonov
 * @date 04.03.2023
 */
class LocationTimestampServiceTest extends Forecast2023ApplicationTests {


    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTimestampRepository locationTimestampRepository;

    @Autowired
    private LocationTimestampMapper locationTimestampMapper;

    @Autowired
    private LocationTimestampService locationTimestampService;

    @BeforeEach
    @AfterEach
    void beforeAndAfterEach() {
        var model = this.locationTimestampMapper.dtoToModel(TestDTOs.getMoscow2000());
        this.locationTimestampRepository.delete(model);
    }

    @Test
    @Transactional
    void testCreate() {
        var model = this.locationTimestampMapper.dtoToModel(TestDTOs.getMoscow2000());
        model.setLocation(this.locationRepository.findAll().stream().findAny().orElseThrow());
        var savedModel = this.locationTimestampService.create(model);
        var receivedModel = this.locationTimestampRepository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }

    @Test
    @Transactional
    void testCreateDuplicate() {
        var model = this.locationTimestampMapper.dtoToModel(TestDTOs.getMoscow2000());
        model.setLocation(this.locationRepository.findAll().stream().findAny().orElseThrow());
        var savedModel = this.locationTimestampService.create(model);
        assertThrows(ResponseStatusException.class, () -> this.locationTimestampService.create(model));
        var receivedModel = this.locationTimestampRepository.findById(savedModel.getId());
        assertThat(receivedModel).isNotNull()
                .isPresent();
        receivedModel.ifPresent((m) -> assertThat(m).isEqualTo(savedModel));
    }
}