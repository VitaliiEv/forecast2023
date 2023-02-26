package vitaliiev.forecast2023.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndLocation",
                columnNames = {"name", "latitude", "longitude"})
})
public class LocationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @NotNull
    @NotBlank
    private String name;

    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String state;

    @NotNull
    @Min(-90)
    @Max(90)
    private BigDecimal latitude;

    @NotNull
    @Min(-180)
    @Max(180)
    private BigDecimal longitude;

    @OneToMany
    private List<LocationTimestampModel> locationTimestamps;

}
