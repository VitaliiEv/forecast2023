package vitaliiev.forecast2023.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import vitaliiev.forecast2023.config.Forecast2023Properties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueNameAndLocation",
                columnNames = {"name", "latitude", "longitude"})
})
public class LocationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Size(max = 20)
    @Pattern(regexp = Forecast2023Properties.LATITUDE_PATTERN)
    private String latitude;

    @NotNull
    @Size(max = 20)
    @Pattern(regexp = Forecast2023Properties.LONGITUDE_PATTERN)
    private String longitude;

    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<LocationTimestampModel> locationTimestamps = new ArrayList<>();

}
