package vitaliiev.forecast2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vitaliiev.forecast2023.dto.ForecastRequest;
import vitaliiev.forecast2023.dto.Location;
import vitaliiev.forecast2023.service.LocationsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 23.02.2023
 */
@Controller
@RequestMapping("/")
public class Forecast {

    @Autowired
    private LocationsService locationsService;

    @GetMapping
    public String getIndex(Model model) {
        return "index";
    }

    @PostMapping
    public String getForecast(@ModelAttribute ForecastRequest forecastRequest, Model model) {
        var locationList = forecastRequest.getLocations()
                .stream()
                .filter(Objects::nonNull)
                .filter(l -> !l.isBlank())
                .collect(Collectors.toList());
        List<String> forecasts = this.locationsService
                .findAllByDescription(locationList)
                .stream()
                .map(Location::toString)
                .collect(Collectors.toList());
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
        model.addAttribute("knownLocations", this.locationsService.findAllAsString());
    }


}
