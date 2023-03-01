package vitaliiev.forecast2023.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import vitaliiev.forecast2023.dto.ForecastRequest;
import vitaliiev.forecast2023.mappers.LocationMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Vitalii Solomonov
 * @date 28.02.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
class ForecastApiImplTest {

    @Value("${forecast2023.rest-api-url}")
    private String apiUrl;

    private static final String FORECAST_URL_SUFFIX = "/forecast";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postLocation() throws Exception {
        var request = new ForecastRequest()
                .addLocationsItem("Moscow");

        var response = mockMvc.perform(post(this.apiUrl + FORECAST_URL_SUFFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // todo check response matcher
                .andReturn();

        // todo check location DB record

        // todo check locationTimestamp
        // todo check forecast
        // todo check response matcher
    }

    @Test
    void postLocations() throws Exception {
        // todo
    }

    @Test
    void postLocationEmptyList() throws Exception {
        // todo
    }

    @Test
    void posthMalformedLocationList() throws Exception {
        // todo
    }

    @Test
    void posthDuplicateLocations() throws Exception {
        // todo
    }
}