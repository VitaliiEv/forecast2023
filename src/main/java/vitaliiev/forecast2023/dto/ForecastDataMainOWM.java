package vitaliiev.forecast2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ForecastDataMainOWM {

  @JsonProperty("temp")
  private Double temperature;

  @JsonProperty("feels_like")
  private Double temperatureFeelsLike;

  @JsonProperty("pressure")
  private Double pressure;

  @JsonProperty("humidity")
  private Integer humidity;
}

