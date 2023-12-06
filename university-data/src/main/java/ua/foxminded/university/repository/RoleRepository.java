package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "select r from Role r where r.role =:role")
    Role findByRole(@Param("role") RoleModel role);
}
