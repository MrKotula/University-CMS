package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "select r from Role r where r.role =:role")
    Role findByRole(@Param("role") RoleModel role);

    @Modifying
    @Query(value = "INSERT INTO schedule.users_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void insertNewRoles(@Param("userId") String userId, @Param("roleId") String roleId);

    @Query(value = "SELECT role_id FROM schedule.users_roles WHERE user_id =:userId", nativeQuery = true)
    List<String> getUserRoles(@Param("userId") String userId);
}
