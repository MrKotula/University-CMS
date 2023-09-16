package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.TeacherAccount;

@Repository
public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, String> {
    @Query("select u from TeacherAccount u where u.email = :email")
    TeacherAccount getTeacherByEmail(@Param("email") String email);
}
