package ua.foxminded.university.service;

import ua.foxminded.university.entity.Schedule;
import java.util.List;

public interface ScheduleService {
    List<Schedule> getListOfScheduleToday(String studentId);

    List<Schedule> getListOfScheduleTomorrow(String studentId);
}
