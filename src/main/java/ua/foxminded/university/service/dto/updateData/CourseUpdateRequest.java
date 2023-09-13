package ua.foxminded.university.service.dto.updateData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequest {

    private String courseId;
    private String courseName;
    private String courseDescription;
}
