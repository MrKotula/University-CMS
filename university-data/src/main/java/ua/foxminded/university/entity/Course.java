package ua.foxminded.university.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"courseId", "courseName", "courseDescription", "version"})
@Builder
@Entity
@Table(name = "courses", schema = "schedule")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_description")
    private String courseDescription;

    @OneToMany(targetEntity = TeacherAccount.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "teachers")
    @JoinTable(
            name = "course_teachers", schema = "schedule",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<TeacherAccount> teachers = new ArrayList<>();

    @Column(name = "number_seats")
    private int numberOfSeats;

    @Column(name = "seats_available")
    private int seatsAvailable;

    @Version
    @Column(name = "version")
    private Integer version;

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public Course(String courseId, String courseName, String courseDescription, int numberOfSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.numberOfSeats = numberOfSeats;
    }

    public void addTeacher(TeacherAccount teacherAccount) {
        this.teachers.add(teacherAccount);
    }

    @Override
    public String toString() {
        return courseName;
    }
}
