package vitaliiev.forecast2023.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vitalii Solomonov
 * @date 02.03.2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ForecastDataNotFound extends Forecast2023Exception {
    private static final String MESSAGE = "Forecast with location timestamp '%s' not found";

    public ForecastDataNotFound() {
    }

    public ForecastDataNotFound(String locationTimestamp) {
        super(String.format(MESSAGE, locationTimestamp));
    }

    public ForecastDataNotFound(String locationTimestamp, Throwable cause) {
        super(String.format(MESSAGE, locationTimestamp), cause);
    }

    public ForecastDataNotFound(Throwable cause) {
        super(cause);
    }
}
