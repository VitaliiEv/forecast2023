package vitaliiev.forecast2023.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vitaliiev.forecast2023.config.Forecast2023Properties;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Getter
@Setter
public class LocationOWM {

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;


    @JsonProperty("lat")
    @NotNull
    @Size(max = 20)
    @Pattern(regexp = Forecast2023Properties.LATITUDE_PATTERN)
    private String latitude;

    @JsonProperty("lon")
    @NotNull
    @Size(max = 20)
    @Pattern(regexp = Forecast2023Properties.LONGITUDE_PATTERN)
    private String longitude;

}
