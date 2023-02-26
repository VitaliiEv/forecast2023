package vitaliiev.forecast2023.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 25.02.2023
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueLocationAndTimestamp",
                columnNames = {"location", "timestamp"})
})
public class LocationTimestampModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PastOrPresent
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "location")
    private LocationModel location;

    @OneToMany
    private List<LocationTimestampModel> locationTimestamps;

}
