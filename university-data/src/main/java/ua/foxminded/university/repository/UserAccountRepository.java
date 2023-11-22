package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.UserAccount;
import java.util.Optional;

@Repository
@Transactional
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    @Query("select u from UserAccount u where u.email = :email")
    Optional<UserAccount> getUserByEmail(@Param("email") String email);
}
