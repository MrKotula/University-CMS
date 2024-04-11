package ua.foxminded.university.validator.impl;

import lombok.AllArgsConstructor;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.validator.ScheduleValidator;
import ua.foxminded.university.validator.ValidationService;
import ua.foxminded.university.validator.exception.ScheduleException;
import java.time.LocalDate;
import java.time.LocalTime;

@ValidationService
@AllArgsConstructor
public class ScheduleValidatorImpl implements ScheduleValidator {

    private final ScheduleRepository scheduleRepository;

    @Override
    public void checkAvailableLectorRoom(ScheduleRequest scheduleRequest) {
        LocalDate dateOfLecture = scheduleRequest.getLecture().getDateOfLecture();
        String lectureRoom = scheduleRequest.getLecture().getLectureRoom();
        LocalTime startOfLecture = scheduleRequest.getLecture().getStartOfLecture();

        if (!scheduleRepository.findSchedulesByDateAndLectorRoom(dateOfLecture, lectureRoom, startOfLecture).isEmpty()) {
            throw new ScheduleException("Lecture room is busy");
        }
    }

    @Override
    public void checkAvailableLectorRoom(Schedule schedule) {
        LocalDate dateOfLecture = schedule.getLecture().getDateOfLecture();
        String lectureRoom = schedule.getLecture().getLectureRoom();
        LocalTime startOfLecture = schedule.getLecture().getStartOfLecture();

        System.out.println(scheduleRepository.findSchedulesByDateAndLectorRoom(dateOfLecture, lectureRoom, startOfLecture));

        if (!scheduleRepository.findSchedulesByDateAndLectorRoom(dateOfLecture, lectureRoom, startOfLecture).isEmpty()) {
            throw new ScheduleException("Lecture room is busy");
        }
    }

    @Override
    public void checkAvailableTeacher(ScheduleRequest scheduleRequest) {
        LocalDate dateOfLecture = scheduleRequest.getLecture().getDateOfLecture();
        String teacherId = scheduleRequest.getTeacher().getUserId();
        LocalTime startOfLecture = scheduleRequest.getLecture().getStartOfLecture();

        if (!scheduleRepository.findSchedulesByDateAndTeacher(dateOfLecture, teacherId, startOfLecture).isEmpty()) {
            throw new ScheduleException("Teacher is busy at this time");
        }
    }

    @Override
    public void checkAvailableTeacher(Schedule schedule) {
        LocalDate dateOfLecture = schedule.getLecture().getDateOfLecture();
        String teacherId = schedule.getTeacher().getUserId();
        LocalTime startOfLecture = schedule.getLecture().getStartOfLecture();

        if (!scheduleRepository.findSchedulesByDateAndTeacher(dateOfLecture, teacherId, startOfLecture).isEmpty()) {
            throw new ScheduleException("Teacher is busy at this time");
        }
    }

    @Override
    public void checkAvailableGroup(ScheduleRequest scheduleRequest) {
        LocalDate dateOfLecture = scheduleRequest.getLecture().getDateOfLecture();
        String groupId = scheduleRequest.getGroup().getGroupId();
        LocalTime startOfLecture = scheduleRequest.getLecture().getStartOfLecture();

        if (!scheduleRepository.findSchedulesByDateAndGroup(dateOfLecture, groupId, startOfLecture).isEmpty()) {
            throw new ScheduleException("Group has lesson at this time");
        }
    }
}
