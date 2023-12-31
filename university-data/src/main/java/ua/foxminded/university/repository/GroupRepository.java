package ua.foxminded.university.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    @Query(value = "SELECT groups.group_id, groups.group_name, COUNT(user_id) FROM schedule.groups "
            + "LEFT JOIN schedule.users ON groups.group_id = users.group_id GROUP BY groups.group_id, groups.group_name "
            + "HAVING COUNT(user_id) <=:studentCount ORDER BY groups.group_id", nativeQuery = true)
    List<Group> getGroupsWithLessEqualsStudentCount(@Param("studentCount") int studentCount);
}
