package vitaliiev.forecast2023.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueLocationAndTimestamp",
                columnNames = {"location", "timestamp"})
})
public class LocationTimestampModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PastOrPresent
    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "location")
    @ToString.Exclude
    private LocationModel location;

    @OneToMany(mappedBy = "locationTimestamp")
    @ToString.Exclude
    private List<ForecastDataModel> forecastData = new ArrayList<>();

}
