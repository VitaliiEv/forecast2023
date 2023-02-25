package vitaliiev.forecast2023.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vitaliiev.forecast2023.dto.ForecastRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Solomonov
 * @date 23.02.2023
 */
@Controller
@RequestMapping("/")
public class Forecast {

    @GetMapping
    public String getIndex(Model model) {
        List<String> knownLocations = new ArrayList<>();
        knownLocations.add("Moscow"); // FIXME: 25.02.2023 fill from database
        knownLocations.add("Moscow, RU");
        knownLocations.add("Moscow, US");
        knownLocations.add("Moscow, Idaho, US");
        knownLocations.add("Paris");
        model.addAttribute("knownLocations", knownLocations);
        return "index";
    }

    @PostMapping
    public String getForecast(@ModelAttribute ForecastRequest forecastRequest, Model model) {
        List<String> forecasts = forecastRequest.getLocations(); // FIXME: 25.02.2023
        model.addAttribute("forecasts", forecasts);
        return "index";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        // todo make UI form instead
        List<String> locations = new ArrayList<>();
        locations.add("Example: Moscow");
        locations.add("Example: Moscow, RU");
        locations.add("Example: Moscow, US");
        locations.add("Example: Moscow, Idaho, US");
        locations.add("Example: Paris");
        model.addAttribute("locations", locations);
    }


}
