package vitaliiev.forecast2023.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vitaliiev.forecast2023.dto.ForecastData;
import vitaliiev.forecast2023.dto.ForecastForLocation;
import vitaliiev.forecast2023.dto.ForecastRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Solomonov
 * @date 23.02.2023
 */
@Controller
@RequestMapping("/")
public class ForecastController {


    private final ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @PostMapping
    public String getForecast(@ModelAttribute ForecastRequest forecastRequest, Model model) {
        var forecast = this.forecastService.getForecast(forecastRequest);
        var dates = forecast.stream()
                .findAny()
                .map(ForecastForLocation::getForecastData)
                .map(fd -> fd.stream()
                        .map(ForecastData::getDatetime)
                        .map(LocalDateTime::toLocalDate)
                        .collect(Collectors.toList()))
                .orElseThrow();
//        var dates = forecast.stream()
//                .map(ForecastForLocation::getLocationTimestamp)
//                .map(LocationTimestamp::getTimestamp)
//                .map(LocalDateTime::toLocalDate)
//                .collect(Collectors.toList());
        model.addAttribute("dates", dates);
        model.addAttribute("forecasts", forecast);
        return "index";
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        List<String> locations = new ArrayList<>();
        locations.add("Example: Moscow");
        locations.add("Example: Moscow, RU");
        locations.add("Example: Moscow, US");
        locations.add("Example: Moscow, Idaho, US");
        locations.add("Example: Paris");
        model.addAttribute("locations", locations);
        model.addAttribute("knownLocations", this.forecastService.findAllAsString());
    }

}
