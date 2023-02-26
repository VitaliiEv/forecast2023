package vitaliiev.forecast2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vitaliiev.forecast2023.domain.ForecastDataModel;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
public interface ForecastDataRepository extends JpaRepository<ForecastDataModel, Long> {

}
