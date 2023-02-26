package vitaliiev.forecast2023.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = {Forecast2023Exception.class})
    protected Model handleForecast2023Exception(Forecast2023Exception ex, Model model) {
        model.addAttribute("errors", ex.getSuppressed());
        return model;
    }
}
