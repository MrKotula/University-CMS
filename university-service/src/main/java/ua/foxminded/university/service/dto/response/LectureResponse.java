package ua.foxminded.university.service.dto.response;

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
public class LectureResponse {
    private LocalTime startOfLecture;
    private LocalTime endOfLecture;
    private LocalDate dateOfLecture;
    private String lectureRoom;

    @Override
    public String toString() {
        return "StartOfLecture=" + startOfLecture + ", endOfLecture=" + endOfLecture +
                ", dateOfLecture=" + dateOfLecture +", lectureRoom=" + lectureRoom;
    }
}
