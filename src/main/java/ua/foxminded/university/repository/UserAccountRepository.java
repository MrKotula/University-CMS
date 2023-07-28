package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Query("select u from UserAccount u where u.email = :email")
    UserAccount getUserByEmail(@Param("email") String email);
}
