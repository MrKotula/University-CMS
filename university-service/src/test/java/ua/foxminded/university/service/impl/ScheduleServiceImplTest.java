package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.request.CourseRequest;
import ua.foxminded.university.service.dto.request.GroupRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.request.TeacherAccountRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.GroupMapper;
import ua.foxminded.university.service.mapper.ScheduleMapper;
import ua.foxminded.university.service.mapper.TeacherAccountMapper;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DateService dateService;

    @Mock
    private StudentAccountService studentAccountService;

    @Mock
    private GroupService groupService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private TeacherAccountRepository teacherAccountRepository;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private TeacherAccountMapper teacherAccountMapper;

    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    void shouldThrowEntityNotFoundExceptionWhenEntityNameIsNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        GroupResponse groupResponse = new GroupResponse("", null, 1);

        when(groupService.getGroupByUserId("")).thenReturn(groupResponse);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleToday(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenEntityIsNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        when(groupService.getGroupByUserId("")).thenReturn(null);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleToday(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetListOfScheduleTomorrowReturnNameNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        GroupResponse groupResponse = new GroupResponse("", null, 1);

        when(groupService.getGroupByUserId("")).thenReturn(groupResponse);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleTomorrow(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenGetListOfScheduleTomorrowNullTest() {
        String expectedMessage = "GroupResponse return a null!";

        when(groupService.getGroupByUserId("")).thenReturn(null);

        Throwable exception = assertThrows(EntityNotFoundException.class, ()-> scheduleService.getListOfScheduleTomorrow(""));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void verifyUseMethodRegister() {
        TeacherAccount teacherAccount = TeacherAccount.teacherBuilder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();

        TeacherAccountRequest teacherAccountRequest = TeacherAccountRequest.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();

        Course course = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);
        CourseRequest courseRequest = new CourseRequest("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        Group group = new Group("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23", "GD-32", 1);
        GroupRequest groupRequest = new GroupRequest("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23", "GD-32", 1);

        Schedule schedule = Schedule.builder()
                .teacher(teacherAccount)
                .group(group)
                .course(course)
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .teacher(teacherAccountRequest)
                .group(groupRequest)
                .course(courseRequest)
                .build();

        when(scheduleMapper.transformScheduleFromDto(scheduleRequest)).thenReturn(schedule);

        scheduleService.register(scheduleRequest);

        verify(scheduleRepository).save(any(Schedule.class));
    }

    @Test
    void shouldReturnListOfStartTimesTest() {
        List<Time> listOfStartTime = new ArrayList<>();
        listOfStartTime.add(Time.valueOf("08:30:00"));

        when(scheduleRepository.getListLectureStartTimes()).thenReturn(listOfStartTime);

        List<LocalTime> listOfStartTimeByLocalTime = scheduleService.getListLectureStartTimes();

        assertEquals(LocalTime.of(8, 30, 00), listOfStartTimeByLocalTime.get(0));
    }

    @Test
    void shouldReturnListOfEndTimesTest() {
        List<Time> listOfEndTime = new ArrayList<>();
        listOfEndTime.add(Time.valueOf("10:05:00"));

        when(scheduleRepository.getListLectureEndTimes()).thenReturn(listOfEndTime);

        List<LocalTime> listOfEndTimeByLocalTime = scheduleService.getListLectureEndTimes();

        assertEquals(LocalTime.of(10, 05, 00), listOfEndTimeByLocalTime.get(0));
    }

    @Test
    void shouldReturnListOfScheduleToday() {
        LocalDate localDate = LocalDate.of(2024,1,27);
        GroupResponse groupResponse = new GroupResponse("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23", "GD-32", 1);

        List<Schedule> listOfScheduleToday = new ArrayList<>();
        List<ScheduleResponse> scheduleResponseListToday = new ArrayList<>();

        when(dateService.getCurrentDate()).thenReturn(localDate);
        when(groupService.getGroupByUserId("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23")).thenReturn(groupResponse);
        when(scheduleRepository.findSchedulesByDateAndGroup(localDate, "GD-32")).thenReturn(listOfScheduleToday);

        assertEquals(scheduleResponseListToday, scheduleService.getListOfScheduleToday("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23"));
    }

    @Test
    void shouldReturnListOfScheduleTomorrow() {
        LocalDate localDate = LocalDate.of(2024,1,27);
        GroupResponse groupResponse = new GroupResponse("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23", "GD-32", 1);

        List<Schedule> listOfScheduleToday = new ArrayList<>();
        List<ScheduleResponse> scheduleResponseListToday = new ArrayList<>();

        when(dateService.getNextDayOfMonth()).thenReturn(localDate);
        when(groupService.getGroupByUserId("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23")).thenReturn(groupResponse);
        when(scheduleRepository.findSchedulesByDateAndGroup(localDate, "GD-32")).thenReturn(listOfScheduleToday);

        assertEquals(scheduleResponseListToday, scheduleService.getListOfScheduleTomorrow("1d95bc79-a549-4d2c-aeb5-3f929aeEgt23"));
    }

    @Test
    void shouldReturnListOfLectureRoomsTest() {
        List<String> listOfLectureRooms = new ArrayList<>();
        listOfLectureRooms.add("c. 453");

        when(scheduleRepository.getListLectureRooms()).thenReturn(listOfLectureRooms);

        assertEquals(listOfLectureRooms, scheduleService.getListLectureRooms());
    }

    @Test
    void shouldReturnScheduleResponseWhenUseCreateScheduleTest() {
        CourseResponse testCourseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);
        Course testCourse = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        GroupResponse testGroupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();
        Group testGroup = Group.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();
        TeacherAccount teacherAccountTest = TeacherAccount.teacherBuilder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testCourse));
        when(teacherAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d8799")).thenReturn(Optional.of(teacherAccountTest));
        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testGroup));
        when(courseMapper.transformCourseToDto(testCourse)).thenReturn(testCourseResponse);
        when(groupMapper.transformGroupToDto(testGroup)).thenReturn(testGroupResponse);
        when(teacherAccountMapper.transformTeacherAccountToDto(teacherAccountTest)).thenReturn(teacherAccountResponseTest);

        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .teacher(teacherAccountResponseTest)
                .group(testGroupResponse)
                .course(testCourseResponse)
                .build();

        assertEquals(scheduleResponse, scheduleService.createSchedule("33c99439-aaf0-4ebd-a07a-bd0c550d8799",
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", "1d95bc79-a549-4d2c-aeb5-3f929aee5432"));
    }

    @Test
    void testGetPreparedScheduleRequest() {
        ScheduleRequestBody scheduleRequestBody = ScheduleRequestBody.builder()
                .selectedStartLecture("08:30:00")
                .selectedEndLecture("10:05:00")
                .selectedDateOfLecture("2023-12-31")
                .selectedLectureRoom("c. 103")
                .selectedCourseId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .selectedGroupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .selectedTeacherId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .build();

        CourseResponse testCourseResponse = new CourseResponse("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);
        Course testCourse = new Course("1d95bc79-a549-4d2c-aeb5-3f929aee5432", "testCourse", "testDescription", 30);

        GroupResponse testGroupResponse = GroupResponse.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();
        Group testGroup = Group.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();
        TeacherAccount teacherAccountTest = TeacherAccount.teacherBuilder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();

        ScheduleResponse scheduleResponse = ScheduleResponse.builder()
                .course(testCourseResponse)
                .teacher(teacherAccountResponseTest)
                .group(testGroupResponse)
                .build();

        ScheduleRequest scheduleRequest = new ScheduleRequest();

        when(courseRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testCourse));
        when(teacherAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d8799")).thenReturn(Optional.of(teacherAccountTest));
        when(groupRepository.findById("1d95bc79-a549-4d2c-aeb5-3f929aee5432")).thenReturn(Optional.of(testGroup));
        when(courseMapper.transformCourseToDto(testCourse)).thenReturn(testCourseResponse);
        when(groupMapper.transformGroupToDto(testGroup)).thenReturn(testGroupResponse);
        when(teacherAccountMapper.transformTeacherAccountToDto(teacherAccountTest)).thenReturn(teacherAccountResponseTest);
        when(scheduleMapper.transformScheduleResponseFromDto(scheduleResponse)).thenReturn(scheduleRequest);

        assertEquals(scheduleRequest, scheduleService.getPreparedScheduleRequest(scheduleRequestBody));
    }
}
