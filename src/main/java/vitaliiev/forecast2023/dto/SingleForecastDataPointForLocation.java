package vitaliiev.forecast2023.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SingleForecastDataPointForLocation {

  @NotNull
  @Valid
  private LocationTimestamp locationTimestamp;


  @NotNull
  @Valid
  private ForecastData forecastData;

}

