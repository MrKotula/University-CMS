package ua.foxminded.university.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecture {
    private LocalTime startOfLecture;

    private LocalTime endOfLecture;

    private LocalDate dateOfLecture;

    private String lectureRoom;
}
