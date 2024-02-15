package ua.foxminded.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timetable", schema = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id")
    @ToString.Exclude
    private String scheduleId;

    @OneToOne
    @ToString.Include(name = "Course")
    private Course course;

    @OneToOne
    @ToString.Include(name = "Group")
    private Group group;

    @OneToOne
    @ToString.Include(name = "Teacher")
    private TeacherAccount teacher;

    @Embedded
    @ToString.Include(name = "Lecture")
    private Lecture lecture;

    @Version
    @Column(name = "version")
    private Integer version;
}
