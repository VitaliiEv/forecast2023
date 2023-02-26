package vitaliiev.forecast2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitaliiev.forecast2023.domain.LocationTimestampModel;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
public interface LocationTimestampRepository extends JpaRepository<LocationTimestampModel, Long> {

}
