package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Course;
import java.util.List;

@Repository
@Transactional
public interface CourseRepository extends JpaRepository<Course, String> {
    @Query(value = "SELECT courses.course_id, courses.course_name, courses.course_description, courses.number_seats, courses.seats_available, courses.version "
            + "FROM schedule.courses INNER JOIN schedule.students_courses ON courses.course_id = students_courses.course_id "
            + "WHERE students_courses.user_id=:userId", nativeQuery = true)
    List<Course> findByStudentId(@Param("userId") String userId);

    @Query(value = "SELECT course_id, course_name, course_description, number_seats, seats_available FROM schedule.courses c "
            + "WHERE NOT EXISTS (SELECT * FROM schedule.students_courses s_c WHERE user_id =:userId AND c.course_id = s_c.course_id)", nativeQuery = true)
    List<Course> getCoursesMissingByStudentId(@Param("userId") String userId);

    @Query(value = "SELECT COUNT(course_id) FROM schedule.students_courses WHERE course_id=:courseId", nativeQuery = true)
    int getCountedCoursesSeats(@Param("courseId") String courseId);

    @Modifying
    @Query(value = "DELETE FROM schedule.courses WHERE course_id=:courseId", nativeQuery = true)
    void removeCourse(@Param("courseId") String courseId);
}
