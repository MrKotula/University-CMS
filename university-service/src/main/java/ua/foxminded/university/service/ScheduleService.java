package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getListOfScheduleToday(String studentId);

    List<ScheduleResponse> getListOfScheduleTomorrow(String studentId);

    void register(ScheduleRequest scheduleRequest);

    ScheduleResponse createSchedule(String teacherId, String groupId, String courseId);

    List<LocalTime> getListLectureStartTimes();

    List<LocalTime> getListLectureEndTimes();

    List<String> getListLectureRooms();

    ScheduleRequest getPreparedScheduleRequest(ScheduleRequestBody scheduleRequestBody);
}
