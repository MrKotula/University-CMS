package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"courseId", "courseName", "courseDescription", "version"})
public class CourseRequest {
    private String courseId;
    private String courseName;
    private String courseDescription;
    private List<TeacherAccountRequest> teachers;
    private int numberOfSeats;
    private int seatsAvailable;
    private Integer version;

    public CourseRequest(String courseId, String courseName, String courseDescription, List<TeacherAccountRequest> teachers) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.teachers = teachers;
    }

    public CourseRequest(String courseId, String courseName, String courseDescription, int numberOfSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String toString() {
        return courseName + ", " + "available seats - " + seatsAvailable;
    }
}
