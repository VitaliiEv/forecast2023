package vitaliiev.forecast2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Setter
@Getter
public class ForecastDataOWM {

    @JsonProperty("dt_txt")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.time.LocalDateTime datetime;

    @JsonProperty("main")
    @Valid
    ForecastDataMainOWM main;

    @JsonProperty("weather")
    @Valid
    List<ForecastDataWeatherOWM> weather;

    @JsonProperty("wind")
    @Valid
    ForecastDataWindOWM wind;


}

