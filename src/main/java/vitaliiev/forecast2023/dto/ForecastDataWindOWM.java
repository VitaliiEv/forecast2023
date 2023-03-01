package vitaliiev.forecast2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForecastDataWindOWM {

    @JsonProperty("speed")
    private Double windSpeed;

    @JsonProperty("deg")
    private Integer windDirection;

}

