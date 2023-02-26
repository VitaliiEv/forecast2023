package vitaliiev.forecast2023.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Entity
@Getter
@Setter
public class ForecastDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDate datetime;

    @NotNull
    private BigDecimal temperature;

    @NotNull
    private BigDecimal temperatureFeelsLike;

    @NotNull
    @Min(0)
    private BigDecimal pressure;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer humidity;

    @NotNull
    @Size(max = 100)
    private String weatherDescription;

    @NotNull
    @Min(0)
    private BigDecimal windSpeed;

    @NotNull
    @Size(max = 50)
    private String windDirection;

    @ManyToOne
    @NotNull
    private LocationTimestampModel locationModel;

}
