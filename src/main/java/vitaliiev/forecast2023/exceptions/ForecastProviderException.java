package vitaliiev.forecast2023.exceptions;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
public class ForecastProviderException extends Forecast2023Exception {
    public ForecastProviderException() {
    }

    public ForecastProviderException(String message) {
        super(message);
    }

    public ForecastProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForecastProviderException(Throwable cause) {
        super(cause);
    }
}
