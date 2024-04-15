package ua.foxminded.university.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.service.dto.request.GroupRequest;
import ua.foxminded.university.service.dto.request.LectureRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.TeacherAccountRequest;
import ua.foxminded.university.service.mapper.ScheduleMapper;
import ua.foxminded.university.validator.exception.ScheduleException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleValidatorImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleValidatorImpl scheduleValidator;

    private ScheduleMapper scheduleMapper = Mappers.getMapper(ScheduleMapper.class);

    @Test
    void shouldReturnScheduleExceptionWhenGroupHasLesson() {
        String expectedMessage = "Group has lesson at this time";

        List<Schedule> listOfSchedule = new ArrayList<>();

        GroupRequest testGroup = GroupRequest.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .group(testGroup)
                .lecture(testLecture)
                .build();

        ScheduleRequest scheduleRequestNew = ScheduleRequest.builder()
                .scheduleId("newScheduleId")
                .group(testGroup)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndGroup(testLecture.getDateOfLecture(),
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableGroup(scheduleRequestNew));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenGroupDontHaveLesson() {
        List<Schedule> listOfSchedule = new ArrayList<>();

        GroupRequest testGroup = GroupRequest.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .group(testGroup)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndGroup(testLecture.getDateOfLecture(),
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        assertDoesNotThrow(() -> scheduleValidator.checkAvailableGroup(scheduleRequest));
    }

    @Test
    void shouldReturnScheduleExceptionWhenTeacherIsBusy()  {
        String expectedMessage = "Teacher is busy at this time";

        List<Schedule> listOfSchedule = new ArrayList<>();

        TeacherAccountRequest teacherAccountResponse = TeacherAccountRequest.builder()
                .userId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .teacher(teacherAccountResponse)
                .lecture(testLecture)
                .build();

        ScheduleRequest scheduleRequestNew = ScheduleRequest.builder()
                .scheduleId("newScheduleId")
                .teacher(teacherAccountResponse)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                teacherAccountResponse.getUserId(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableTeacher(scheduleRequestNew));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowWhenTeacherIsNotBusy()  {
        List<Schedule> listOfSchedule = new ArrayList<>();

        TeacherAccountRequest teacherAccountResponse = TeacherAccountRequest.builder()
                .userId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .teacher(teacherAccountResponse)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                teacherAccountResponse.getUserId(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        assertDoesNotThrow(()-> scheduleValidator.checkAvailableTeacher(scheduleRequest));
    }

    @Test
    void shouldReturnScheduleExceptionWhenTeacherIsBusyForSchedule()  {
        String expectedMessage = "Teacher is busy at this time";

        List<Schedule> listOfSchedule = new ArrayList<>();

        TeacherAccount teacherAccount = TeacherAccount.teacherBuilder()
                .userId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        Lecture testLecture = Lecture.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        Schedule schedule = Schedule.builder()
                .scheduleId("existingScheduleId")
                .teacher(teacherAccount)
                .lecture(testLecture)
                .build();

        Schedule scheduleNew = Schedule.builder()
                .scheduleId("newScheduleId")
                .teacher(teacherAccount)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(schedule);

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                teacherAccount.getUserId(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableTeacher(scheduleNew));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoesNotThrowWhenTeacherIsNotBusyForSchedule()  {
        List<Schedule> listOfSchedule = new ArrayList<>();

        TeacherAccount teacherAccount = TeacherAccount.teacherBuilder()
                .userId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        Lecture testLecture = Lecture.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        Schedule schedule = Schedule.builder()
                .scheduleId("existingScheduleId")
                .teacher(teacherAccount)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(schedule);

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                teacherAccount.getUserId(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        assertDoesNotThrow(()-> scheduleValidator.checkAvailableTeacher(schedule));
    }

    @Test
    void shouldReturnScheduleExceptionWhenLectorRoomIsBusy() {
        String expectedMessage = "Lecture room is busy";

        List<Schedule> listOfSchedule = new ArrayList<>();

        LectureRequest testLecture = LectureRequest.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        ScheduleRequest scheduleRequestNew = ScheduleRequest.builder()
                .scheduleId("newScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                testLecture.getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableLectorRoom(scheduleRequestNew));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenLectorRoomIsNotBusy() {
        List<Schedule> listOfSchedule = new ArrayList<>();

        LectureRequest testLecture = LectureRequest.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .scheduleId("existingScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                testLecture.getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        assertDoesNotThrow(() -> scheduleValidator.checkAvailableLectorRoom(scheduleRequest));
    }

    @Test
    void shouldReturnScheduleExceptionWhenLectorRoomIsBusyForSchedule() {
        String expectedMessage = "Lecture room is busy";

        List<Schedule> listOfSchedule = new ArrayList<>();

        Lecture testLecture = Lecture.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        Schedule schedule = Schedule.builder()
                .scheduleId("existingScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        Schedule scheduleNew = Schedule.builder()
                .scheduleId("newScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(schedule);

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                testLecture.getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableLectorRoom(scheduleNew));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenLectorRoomIsNotBusyForSchedule() {
        List<Schedule> listOfSchedule = new ArrayList<>();

        Lecture testLecture = Lecture.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 0))
                .build();

        Schedule schedule = Schedule.builder()
                .scheduleId("existingScheduleId")
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(schedule);

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                testLecture.getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        assertDoesNotThrow(() -> scheduleValidator.checkAvailableLectorRoom(schedule));
    }
}
