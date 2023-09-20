package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.TeacherAccount;

@Repository
@Transactional
public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, String> {
    @Query("select u from TeacherAccount u where u.email = :email")
    TeacherAccount getTeacherByEmail(@Param("email") String email);

    @Modifying
    @Query(value = "INSERT INTO schedule.diploma_students(user_id, user_id_student) VALUES (:userId, :userIdStudent)", nativeQuery = true)
    void addStudentToScienceSupervisor(@Param("userId") String userId, @Param("userIdStudent") String userIdStudent);
}
