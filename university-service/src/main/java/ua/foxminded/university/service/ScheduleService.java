package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> getListOfScheduleToday(String studentId);

    List<ScheduleResponse> getListOfScheduleTomorrow(String studentId);

    void register(ScheduleRequestBody scheduleRequestBody);

    ScheduleResponse createSchedule(String teacherId, String groupId, String courseId);

    List<LocalTime> getListLectureStartTimes();

    List<LocalTime> getListLectureEndTimes();

    List<String> getListLectureRooms();

    ScheduleRequest getPreparedScheduleRequest(ScheduleRequestBody scheduleRequestBody);

    List<ScheduleResponse> getListGroupSchedule(String groupId);

    ScheduleResponse getSchedule(String scheduleId);

    void updateSchedule(String scheduleId, ScheduleRequestBody scheduleRequestBody);
}
