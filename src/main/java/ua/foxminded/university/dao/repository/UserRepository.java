package ua.foxminded.university.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.foxminded.university.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
