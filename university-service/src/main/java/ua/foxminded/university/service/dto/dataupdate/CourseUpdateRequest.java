package ua.foxminded.university.service.dto.dataupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.TeacherAccount;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {
    private String courseId;
    private String courseName;
    private String courseDescription;
    private List<TeacherAccount> teachers;
    private Integer version;
}
