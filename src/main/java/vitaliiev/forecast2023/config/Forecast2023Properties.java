package vitaliiev.forecast2023.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import vitaliiev.forecast2023.openweathermap.reducers.DefaultForecastDataReducer;
import vitaliiev.forecast2023.openweathermap.reducers.ForecastDataReducer;
import vitaliiev.forecast2023.openweathermap.reducers.NoopForecastDataReducer;

import java.time.Duration;

/**
 * @author Vitalii Solomonov
 * @date 23.02.2023
 */
@Component
@ConfigurationProperties(prefix = "forecast2023")
@Getter
@Setter
public class Forecast2023Properties {

    public static final String LATITUDE_PATTERN = "^-?(((\\d|[0-8]\\d).\\d{4,})|(90.0{4,}))$";

    public static final String LONGITUDE_PATTERN = "^-?(((\\d|\\d\\d|1[0-7]\\d).\\d{4,})|(180.0{4,}))$";

    @NotBlank
    @NotNull
    private String owmApiKey;

    private String units = "metric";

    private String lang = "ru";

    private String restApiUrl = "http://localhost:8080/api/v1";

    private Duration obsolete = Duration.ofHours(1);

    private String dataPointsReducer = "default";

    public ForecastDataReducer getForecastDataReducer() {
        if (this.dataPointsReducer.equals("noop")) {
            return new NoopForecastDataReducer();
        }
        return new DefaultForecastDataReducer();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("messages/Messages");
        return messageSource;
    }
}
