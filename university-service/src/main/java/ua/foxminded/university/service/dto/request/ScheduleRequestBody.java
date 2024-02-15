package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleRequestBody {
    private String selectedTeacherId;
    private String selectedGroupId;
    private String selectedCourseId;
    private String selectedStartLecture;
    private String selectedEndLecture;
    private String selectedDateOfLecture;
    private String selectedLectureRoom;
}
