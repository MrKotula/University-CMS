package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.TeacherAccount;

@Repository
public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, String> {

}
