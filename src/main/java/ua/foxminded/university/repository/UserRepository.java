package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.User;
import ua.foxminded.university.entity.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.email = :email")
    UserAccount getUserByEmail(@Param("email") String email);
}
