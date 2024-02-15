package ua.foxminded.university.validator.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.Schedule;
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
import java.util.Collections;
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
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .group(testGroup)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndGroup(testLecture.getDateOfLecture(),
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableGroup(scheduleRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenGroupDontHaveLesson() {
        GroupRequest testGroup = GroupRequest.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .group(testGroup)
                .lecture(testLecture)
                .build();

        when(scheduleRepository.findSchedulesByDateAndGroup(testLecture.getDateOfLecture(),
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", testLecture.getStartOfLecture())).thenReturn(Collections.emptyList());

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
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .teacher(teacherAccountResponse)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                teacherAccountResponse.getUserId(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableTeacher(scheduleRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenTeacherDontBusy() {
        TeacherAccountRequest teacherAccountResponse = TeacherAccountRequest.builder()
                .userId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .teacher(teacherAccountResponse)
                .lecture(testLecture)
                .build();

        when(scheduleRepository.findSchedulesByDateAndTeacher(testLecture.getDateOfLecture(),
                "1d95bc79-a549-4d2c-aeb5-3f929aee5432", testLecture.getStartOfLecture())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> scheduleValidator.checkAvailableTeacher(scheduleRequest));
    }

    @Test
    void shouldReturnScheduleExceptionWhenLectorRoomIsBusy() {
        String expectedMessage = "Lecture room is busy";

        List<Schedule> listOfSchedule = new ArrayList<>();

        LectureRequest testLecture = LectureRequest.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .lecture(testLecture)
                .lecture(testLecture)
                .build();

        listOfSchedule.add(scheduleMapper.transformScheduleFromDto(scheduleRequest));

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                testLecture.getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(listOfSchedule);

        Exception exception = assertThrows(ScheduleException.class, () -> scheduleValidator.checkAvailableLectorRoom(scheduleRequest));

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void shouldDoNotThrowWhenLectorRoomIsNotBusy() {
        GroupRequest testGroup = GroupRequest.builder()
                .groupId("1d95bc79-a549-4d2c-aeb5-3f929aee5432")
                .groupName("DT-43")
                .build();

        LectureRequest testLecture = LectureRequest.builder()
                .lectureRoom("c. 312")
                .dateOfLecture(LocalDate.of(2024, 1, 1))
                .startOfLecture(LocalTime.of(8, 30, 00))
                .build();

        ScheduleRequest scheduleRequest = ScheduleRequest.builder()
                .group(testGroup)
                .lecture(testLecture)
                .build();

        when(scheduleRepository.findSchedulesByDateAndLectorRoom(testLecture.getDateOfLecture(),
                scheduleRequest.getLecture().getLectureRoom(), testLecture.getStartOfLecture())).thenReturn(Collections.emptyList());

        assertDoesNotThrow(() -> scheduleValidator.checkAvailableLectorRoom(scheduleRequest));
    }
}
