package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.service.RoleService;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@ContextConfiguration(initializers = {RoleServiceImplTest.Initializer.class})
@Testcontainers
class RoleServiceImplTest {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

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
    @Transactional
    void shouldReturnListOfRolesWhenUseFindAllRoles() {
        List<Role> rolesTest = new ArrayList<>();

        Role roleAdmin = roleRepository.findByRole(RoleModel.ADMIN);
        Role roleModerator = roleRepository.findByRole(RoleModel.MODERATOR);
        Role roleStudent = roleRepository.findByRole(RoleModel.STUDENT);
        Role roleTeacher = roleRepository.findByRole(RoleModel.TEACHER);
        Role roleUser = roleRepository.findByRole(RoleModel.USER);

        rolesTest.add(roleAdmin);
        rolesTest.add(roleModerator);
        rolesTest.add(roleStudent);
        rolesTest.add(roleTeacher);
        rolesTest.add(roleUser);

        assertEquals(rolesTest ,roleService.findAllRoles());
    }
}
