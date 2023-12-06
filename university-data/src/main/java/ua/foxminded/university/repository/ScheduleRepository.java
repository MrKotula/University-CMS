package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Schedule;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository <Schedule, String> {

    @Query("SELECT s FROM Schedule s JOIN s.lecture l JOIN s.group g WHERE l.dateOfLecture = :currentDate " +
            "AND g.groupName = :groupName")
    List<Schedule> findSchedulesByDateAndGroup(@Param("currentDate") LocalDate currentDate, @Param("groupName") String groupName);
}
