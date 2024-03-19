package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequest {
    private String scheduleId;
    private CourseRequest course;
    private GroupRequest group;
    private TeacherAccountRequest teacher;
    private LectureRequest lecture;
    private Integer version;
}
