package ua.foxminded.university.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.university.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, String> {

}
