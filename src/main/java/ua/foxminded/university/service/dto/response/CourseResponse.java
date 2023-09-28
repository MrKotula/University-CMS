package ua.foxminded.university.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.TeacherAccount;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponse {
    private String courseId;
    private String courseName;
    private String courseDescription;
    private List<TeacherAccount> teachers;
    private int numberOfSeats;
    private int seatsAvailable;

    public CourseResponse(String courseId, String courseName, String courseDescription, List<TeacherAccount> teachers) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.teachers = teachers;
    }
}
