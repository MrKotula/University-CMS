package ua.foxminded.university.service.dto.response;

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
@EqualsAndHashCode(of = {"courseId", "courseName"})
public class CourseResponse {
    private String courseId;
    private String courseName;
    private String courseDescription;
    private List<TeacherAccountResponse> teachers;
    private int numberOfSeats;
    private int seatsAvailable;

    public CourseResponse(String courseId, String courseName, String courseDescription, List<TeacherAccountResponse> teachers) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.teachers = teachers;
    }

    public CourseResponse(String courseId, String courseName, String courseDescription, int numberOfSeats) {
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
