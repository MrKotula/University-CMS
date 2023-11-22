package ua.foxminded.university.validator;

import ua.foxminded.university.service.dto.request.ScheduleRequest;

public interface ScheduleValidator {

    void checkAvailableLectorRoom (ScheduleRequest scheduleRequest);

    void checkAvailableTeacher(ScheduleRequest scheduleRequest);

    void checkAvailableGroup(ScheduleRequest scheduleRequest);
}
