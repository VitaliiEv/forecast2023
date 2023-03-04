package vitaliiev.forecast2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitaliiev.forecast2023.domain.LocationTimestampModel;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Repository
public interface LocationTimestampRepository extends JpaRepository<LocationTimestampModel, Long> {

}
