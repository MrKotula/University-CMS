package ua.foxminded.university.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponse {
    private String scheduleId;
    private CourseResponse course;
    private GroupResponse group;
    private TeacherAccountResponse teacher;
    private LectureResponse lecture;
}
