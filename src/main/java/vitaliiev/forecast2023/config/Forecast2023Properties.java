package vitaliiev.forecast2023.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;

/**
 * @author Vitalii Solomonov
 * @date 23.02.2023
 */
@Component
@ConfigurationProperties(prefix = "forecast2023")
@Getter
@Setter
@Validated
public class Forecast2023Properties {
    @NotBlank
    @NotNull
    private String owmApiKey;

    private String units = "metric"; //todo get this data from user locale settings

    private String lang = "en"; //todo get this data from user locale settings

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultLocale(Locale.ENGLISH);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasename("messages/Messages");
        return messageSource;
    }

}
