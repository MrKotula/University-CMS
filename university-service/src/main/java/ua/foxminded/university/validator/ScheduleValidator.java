package ua.foxminded.university.validator;

import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.service.dto.request.ScheduleRequest;

public interface ScheduleValidator {

    void checkAvailableLectorRoom (ScheduleRequest scheduleRequest);

    void checkAvailableLectorRoom(Schedule schedule);

    void checkAvailableTeacher(ScheduleRequest scheduleRequest);

    void checkAvailableTeacher(Schedule schedule);

    void checkAvailableGroup(ScheduleRequest scheduleRequest);
}
