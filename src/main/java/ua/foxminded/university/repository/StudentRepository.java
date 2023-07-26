package ua.foxminded.university.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.enums.Status;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query(value = "SELECT * FROM schedule.students WHERE user_id IN (SELECT user_id FROM schedule.students_courses "
    	+ "WHERE course_id IN (SELECT course_id FROM schedule.courses WHERE course_name =:courseName))", nativeQuery = true)
    List<Student> findByCourseName(@Param("courseName") String courseName);
    
    @Modifying
    @Query(value = "update Student c SET c.status =:status WHERE c.userId =:userId")
    void updateStatus(@Param("status") Status status, @Param("userId") String userId);
    
    @Modifying
    @Query(value = "update Student c SET c.groupId =:groupId, c.status =STUDENT, c.registrationStatus =REGISTERED WHERE userId =:userId")
    void changeGroup(@Param("groupId") String groupId, @Param("userId") String userId);
    
    @Modifying
    @Query(value = "INSERT INTO schedule.students_courses(user_id, course_id) VALUES (:userId, :courseId)", nativeQuery = true)
    void addStudentCourse(@Param("userId") String userId, @Param("courseId") String courseId);
    
    @Modifying
    @Query(value = "DELETE FROM schedule.students_courses WHERE user_id = :userId AND course_id = :courseId", nativeQuery = true)
    void removeStudentFromCourse(@Param("userId") String userId, @Param("courseId") String courseId);  
}
