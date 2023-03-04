package vitaliiev.forecast2023;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataConfiguration {

    @Bean(name = "testData")
    public TestData initTestData() {
        return new TestData();
    }
}
