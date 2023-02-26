package vitaliiev.forecast2023.exceptions;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
public class Forecast2023Exception extends RuntimeException {
    public Forecast2023Exception() {
    }

    public Forecast2023Exception(String message) {
        super(message);
    }

    public Forecast2023Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Forecast2023Exception(Throwable cause) {
        super(cause);
    }
}
