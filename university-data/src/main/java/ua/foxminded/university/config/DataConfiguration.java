package ua.foxminded.university.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ua.foxminded.university")
@SpringBootConfiguration
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class DataConfiguration {

}
