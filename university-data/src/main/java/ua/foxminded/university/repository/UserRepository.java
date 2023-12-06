package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.User;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.email = :email")
    Optional<User> getUserByEmail(@Param("email") String email);
}
