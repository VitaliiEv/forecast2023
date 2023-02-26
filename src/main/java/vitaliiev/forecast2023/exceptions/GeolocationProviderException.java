package vitaliiev.forecast2023.exceptions;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
public class GeolocationProviderException extends Forecast2023Exception {
    public GeolocationProviderException() {
    }

    public GeolocationProviderException(String message) {
        super(message);
    }

    public GeolocationProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeolocationProviderException(Throwable cause) {
        super(cause);
    }
}
