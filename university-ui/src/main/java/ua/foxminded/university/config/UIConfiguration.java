package ua.foxminded.university.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("ua.foxminded.university")
@Import(ServiceConfiguration.class)
public class UIConfiguration {
}
