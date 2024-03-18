package ua.foxminded.university.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userDetails;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> {
                            requests.requestMatchers("/", "/about", "registration", "/login").permitAll();
                            requests.requestMatchers("main.css").permitAll();
                            requests.requestMatchers("/user").hasAnyAuthority("USER", "STUDENT", "TEACHER", "ADMIN", "MODERATOR");
                            requests.requestMatchers("/admin", "/admin/**").hasAuthority("ADMIN");
                            requests.requestMatchers("/teacher", "/teacher/**").hasAnyAuthority("TEACHER");
                            requests.requestMatchers("/student", "/student/info/**", "/student/**").hasAnyAuthority("STUDENT", "TEACHER", "ADMIN");
                            requests.requestMatchers("/user/moderator", "/user/moderator/**").hasAnyAuthority("ADMIN", "MODERATOR");
                            requests.requestMatchers("/user/group", "/user/group/**", "/user/groups/all", "/user/groups/**").hasAnyAuthority("ADMIN", "MODERATOR");
                            requests.anyRequest().authenticated();
                        }
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/user")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
