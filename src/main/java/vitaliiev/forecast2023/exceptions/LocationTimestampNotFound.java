package vitaliiev.forecast2023.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitalii Solomonov
 * @date 02.03.2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LocationTimestampNotFound extends Forecast2023Exception {
    private static final String MESSAGE = "Location timestamp for location '%s' not found";

    public LocationTimestampNotFound() {
    }

    public LocationTimestampNotFound(String location) {
        super(String.format(MESSAGE, location));
    }

    public LocationTimestampNotFound(String location, Throwable cause) {
        super(String.format(MESSAGE, location), cause);
    }

    public LocationTimestampNotFound(Throwable cause) {
        super(cause);
    }
}
