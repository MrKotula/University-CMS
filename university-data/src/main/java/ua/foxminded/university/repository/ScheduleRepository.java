package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Schedule;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository <Schedule, String> {

    @Query("SELECT s FROM Schedule s JOIN s.lecture l JOIN s.group g WHERE l.dateOfLecture = :currentDate " +
            "AND g.groupName = :groupName")
    List<Schedule> findSchedulesByDateAndGroup(@Param("currentDate") LocalDate currentDate, @Param("groupName") String groupName);

    @Query(value = "SELECT * FROM schedule.lecture_start_time", nativeQuery = true)
    List<Time> getListLectureStartTimes();

    @Query(value = "SELECT * FROM schedule.lecture_end_time", nativeQuery = true)
    List<Time> getListLectureEndTimes();

    @Query(value = "SELECT * FROM schedule.lecture_rooms", nativeQuery = true)
    List<String> getListLectureRooms();

    @Query(value = "SELECT s FROM Schedule s WHERE s.lecture.dateOfLecture = :dateOfLecture AND s.lecture.lectureRoom = :lectorRoom AND s.lecture.startOfLecture = :startOfLecture")
    List<Schedule> findSchedulesByDateAndLectorRoom(@Param("dateOfLecture") LocalDate dateOfLecture, @Param("lectorRoom") String lectorRoom, @Param("startOfLecture") LocalTime startOfLecture);

    @Query(value = "SELECT * FROM schedule.timetable WHERE date_of_lecture = :dateOfLecture AND teacher_user_id = :teacherId AND start_of_lecture = :startOfLecture", nativeQuery = true)
    List<Schedule> findSchedulesByDateAndTeacher(@Param("dateOfLecture") LocalDate dateOfLecture, @Param("teacherId") String teacherId, @Param("startOfLecture") LocalTime startOfLecture);

    @Query(value = "SELECT * FROM schedule.timetable WHERE date_of_lecture = :dateOfLecture AND group_group_id = :groupId AND start_of_lecture = :startOfLecture", nativeQuery = true)
    List<Schedule> findSchedulesByDateAndGroup(@Param("dateOfLecture") LocalDate dateOfLecture, @Param("groupId") String groupId, @Param("startOfLecture") LocalTime startOfLecture);

    @Query(value = "SELECT * FROM schedule.timetable WHERE date_of_lecture BETWEEN :firstDateOfLecture AND :lastDateOfLecture", nativeQuery = true)
    List<Schedule> findSchedulesByDateTwoWeek(@Param("firstDateOfLecture") LocalDate firstDateOfLecture, @Param("lastDateOfLecture") LocalDate lastDateOfLecture);

    List<Schedule> findByGroupGroupId(String groupId);

    @Query("SELECT DISTINCT s FROM Schedule s " +
            "LEFT JOIN FETCH s.course " +
            "LEFT JOIN FETCH s.group " +
            "LEFT JOIN FETCH s.teacher " +
            "LEFT JOIN FETCH s.lecture")
    List<Schedule> findAllWithAssociatedEntities();
}
