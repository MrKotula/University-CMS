package ua.foxminded.university.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {LoginTest.Initializer.class})
@Testcontainers
class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.2")
            .withDatabaseName("integration-tests-db").withUsername("sa").withPassword("sa");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    static void setUp() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void accessDeniedTest() throws Exception {
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void correctLoginTest() throws Exception {
        mockMvc.perform(formLogin().user("admin@").password("1234"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }

    @Test
    void invalidLoginTest() throws Exception {
        mockMvc.perform(formLogin().user("test3@gmail.com").password("1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    void loginPageTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
