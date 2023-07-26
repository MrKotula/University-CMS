package ua.foxminded.university.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.GroupService;

@SpringBootTest
@ContextConfiguration(initializers = {GroupServiceImplTest.Initializer.class})
@Testcontainers
class GroupServiceImplTest {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    Group testGroup = new Group("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "DT-43");
    Group testGroupOR = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "OR-41");
    Group testGroupGM = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce1234", "GM-87");
    Group testGroupXI = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "XI-12");
    Group testGroupYT = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce2356", "YT-16");
    Group testGroupTH = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce2344", "TH-13");
    Group testGroupTT = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce1111", "TT-12");
    Group testGroupLG = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce8906", "LG-55");
    Group testGroupGN = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce2337", "GN-33");
    Group testGroupGQ = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce5775", "GQ-22");
    Group testGroupIT = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce9988", "IT-18");
    List<Group> testListOfGroups = Arrays.asList(testGroupOR, testGroupGM, testGroupXI, testGroupTT, testGroupYT,
            testGroupLG, testGroupGQ, testGroupTH, testGroupGN, testGroupIT);

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
    void verifyUseMethodRegister() throws ValidationException {
        Group group = new Group("DT-43");
        groupService.register("DT-43");

        assertEquals(group.getGroupName(), groupRepository.findAll().get(10).getGroupName());
    }

    @Test
    @Transactional
    void verifyUseMethodUpdateGroupName() throws ValidationException {
        testGroup = new Group("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2", "TE-55");
        groupService.updateGroupName(testGroup);

        assertEquals(Optional.of(testGroup), groupRepository.findById("3c01e6f1-762e-43b8-a6e1-7cf493ce92e2"));
    }

    @Test
    @Transactional
    void shouldReturnListOfGroupsWhenUseGetGroupsWithLessEqualsStudentCount() {
        List<Group> testListOfGroups = Arrays.asList(testGroupTT, testGroupGM, testGroupGN, testGroupTH, testGroupYT,
                testGroupXI, testGroupGQ, testGroupLG, testGroupOR, testGroupIT);
        testGroupXI.setCount(1);
        testGroupOR.setCount(1);

        assertEquals(testListOfGroups, groupService.getGroupsWithLessEqualsStudentCount(55));
    }
}
