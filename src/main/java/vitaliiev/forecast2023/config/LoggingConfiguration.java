package vitaliiev.forecast2023.config;

import com.github.sidssids.blocklogger.spring.aspect.LogBlockInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import vitaliiev.forecast2023.aop.LoggingAspect;

/**
 * @author Vitalii Solomonov
 * @date 26.02.2023
 */
@Configuration
@EnableAspectJAutoProxy
@Profile("dev")
public class LoggingConfiguration {
    @Bean
    public LoggingAspect loggingAspect(Environment env, LogBlockInterceptor logBlockInterceptor) {
        return new LoggingAspect(logBlockInterceptor, env);
    }

    @Bean
    public LogBlockInterceptor logBlockInterceptor() {
        return new LogBlockInterceptor();
    }
}
