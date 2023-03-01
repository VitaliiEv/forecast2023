package vitaliiev.forecast2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 27.02.2023
 */
@Getter
@Setter
public class ForecastForLocationOWM {

    @JsonProperty("list")
    @Valid
    List<ForecastDataOWM> forecastData = new ArrayList<>();

}
