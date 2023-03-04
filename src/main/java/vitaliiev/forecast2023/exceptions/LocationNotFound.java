package vitaliiev.forecast2023.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitalii Solomonov
 * @date 02.03.2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LocationNotFound extends Forecast2023Exception {
    private static final String MESSAGE = "Location '%s' not found";

    public LocationNotFound() {
    }

    public LocationNotFound(String location) {
        super(String.format(MESSAGE, location));
    }

    public LocationNotFound(String location, Throwable cause) {
        super(String.format(MESSAGE, location), cause);
    }

    public LocationNotFound(Throwable cause) {
        super(cause);
    }
}
