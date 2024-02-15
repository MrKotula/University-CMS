package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureRequest {
    private LocalTime startOfLecture;
    private LocalTime endOfLecture;
    private LocalDate dateOfLecture;
    private String lectureRoom;
}
