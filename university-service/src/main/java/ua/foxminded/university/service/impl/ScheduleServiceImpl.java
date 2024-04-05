package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Schedule;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.repository.ScheduleRepository;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.service.dto.request.LectureRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.request.TeacherAccountRequest;
import ua.foxminded.university.service.dto.response.CourseResponse;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.service.mapper.CourseMapper;
import ua.foxminded.university.service.mapper.GroupMapper;
import ua.foxminded.university.service.mapper.ScheduleMapper;
import ua.foxminded.university.service.mapper.TeacherAccountMapper;
import ua.foxminded.university.validator.ScheduleValidator;
import ua.foxminded.university.validator.exception.EntityNotFoundException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DateService dateService;
    private final StudentAccountService studentAccountService;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherAccountRepository teacherAccountRepository;

    private final ScheduleMapper scheduleMapper;
    private final CourseMapper courseMapper;
    private final GroupMapper groupMapper;
    private final TeacherAccountMapper teacherAccountMapper;

    private final ScheduleValidator scheduleValidator;

    @Override
    public List<ScheduleResponse> getListOfScheduleToday(String studentId) {
        GroupResponse groupResponse = groupService.getGroupByUserId(studentId);

        LocalDate currentDate = dateService.getCurrentDate();

        if(groupResponse == null || groupResponse.getGroupName() == null) {
            throw new EntityNotFoundException("GroupResponse return a null!");
        }

        List<Schedule> listOfScheduleToday = scheduleRepository.findSchedulesByDateAndGroup(currentDate, groupResponse.getGroupName());

        return scheduleMapper.transformListSchedulesToDto(listOfScheduleToday);
    }

    @Override
    public List<ScheduleResponse> getListOfScheduleTomorrow(String studentId) {
        GroupResponse groupResponse = groupService.getGroupByUserId(studentId);

        if(groupResponse == null || groupResponse.getGroupName() == null) {
            throw new EntityNotFoundException("GroupResponse return a null!");
        }

        List<Schedule> listOfScheduleTomorrow = scheduleRepository.findSchedulesByDateAndGroup(dateService.getNextDayOfMonth(), groupResponse.getGroupName());

        return scheduleMapper.transformListSchedulesToDto(listOfScheduleTomorrow);
    }

    @Override
    public void register(ScheduleRequestBody scheduleRequestBody) {
        ScheduleRequest scheduleRequest = getPreparedScheduleRequest(scheduleRequestBody);

        scheduleValidator.checkAvailableLectorRoom(scheduleRequest);
        scheduleValidator.checkAvailableTeacher(scheduleRequest);
        scheduleValidator.checkAvailableGroup(scheduleRequest);

        Schedule newSchedule = scheduleMapper.transformScheduleFromDto(scheduleRequest);

        scheduleRepository.save(newSchedule);
    }

    @Override
    public ScheduleResponse createSchedule(String teacherId, String groupId, String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course not found!"));
        TeacherAccount teacher = teacherAccountRepository.findById(teacherId).orElseThrow(() -> new EntityNotFoundException("TeacherAccount not found!"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found!"));

        CourseResponse courseResponse = courseMapper.transformCourseToDto(course);
        TeacherAccountResponse teacherAccountResponse = teacherAccountMapper.transformTeacherAccountToDto(teacher);
        GroupResponse groupResponse = groupMapper.transformGroupToDto(group);

        return ScheduleResponse.builder()
                .course(courseResponse)
                .teacher(teacherAccountResponse)
                .group(groupResponse)
                .build();
    }

    @Override
    public List<LocalTime> getListLectureStartTimes() {
        return scheduleRepository.getListLectureStartTimes().stream()
                .map(Time::toLocalTime)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalTime> getListLectureEndTimes() {
        return scheduleRepository.getListLectureEndTimes().stream()
                .map(Time::toLocalTime)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getListLectureRooms() {
        return scheduleRepository.getListLectureRooms().stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleRequest getPreparedScheduleRequest(ScheduleRequestBody scheduleRequestBody) {
        LectureRequest lectureRequest = LectureRequest.builder()
                .startOfLecture(LocalTime.parse(scheduleRequestBody.getSelectedStartLecture()))
                .endOfLecture(LocalTime.parse(scheduleRequestBody.getSelectedEndLecture()))
                .dateOfLecture(LocalDate.parse(scheduleRequestBody.getSelectedDateOfLecture()))
                .lectureRoom(scheduleRequestBody.getSelectedLectureRoom())
                .build();

        ScheduleResponse scheduleResponse = createSchedule(scheduleRequestBody.getSelectedTeacherId(),
                scheduleRequestBody.getSelectedGroupId(), scheduleRequestBody.getSelectedCourseId());

        ScheduleRequest scheduleRequest = scheduleMapper.transformScheduleResponseFromDto(scheduleResponse);

        scheduleRequest.setLecture(lectureRequest);

        return scheduleRequest;
    }

    @Override
    public List<ScheduleResponse> getListGroupSchedule(String groupId) {
        List<Schedule> listGroupSchedule = scheduleRepository.findByGroupGroupId(groupId);

        return scheduleMapper.transformListSchedulesToDto(listGroupSchedule);
    }

    @Override
    public ScheduleResponse getSchedule(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));

        return scheduleMapper.transformScheduleToDto(schedule);
    }

    @Override
    public void updateSchedule(String scheduleId, ScheduleRequestBody scheduleRequestBody) {
        ScheduleRequest scheduleRequest = buildScheduleRequest(scheduleId);
        TeacherAccountRequest teacherAccountRequest = buildSTeacherAccountRequest(scheduleRequestBody);
        LectureRequest lectureRequest = buildLectureRequest(scheduleRequestBody);

        scheduleRequest.setLecture(lectureRequest);
        scheduleRequest.setTeacher(teacherAccountRequest);

        scheduleValidator.checkAvailableLectorRoom(scheduleRequest);
        scheduleValidator.checkAvailableTeacher(scheduleRequest);

        Schedule updatedSchedule = scheduleMapper.transformScheduleFromDto(scheduleRequest);

        scheduleRepository.save(updatedSchedule);
    }

    private LectureRequest buildLectureRequest(ScheduleRequestBody scheduleRequestBody) {
        return LectureRequest.builder()
                .startOfLecture(LocalTime.parse(scheduleRequestBody.getSelectedStartLecture()))
                .endOfLecture(LocalTime.parse(scheduleRequestBody.getSelectedEndLecture()))
                .dateOfLecture(LocalDate.parse(scheduleRequestBody.getSelectedDateOfLecture()))
                .lectureRoom(scheduleRequestBody.getSelectedLectureRoom())
                .build();
    }

    private ScheduleRequest buildScheduleRequest(String scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + scheduleId));

        return scheduleMapper.transformScheduleToDtoRequest(schedule);
    }

    private TeacherAccountRequest buildSTeacherAccountRequest(ScheduleRequestBody scheduleRequestBody) {
        TeacherAccount teacherAccount = teacherAccountRepository.findById(scheduleRequestBody.getSelectedTeacherId())
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + scheduleRequestBody.getSelectedTeacherId()));

        return teacherAccountMapper.transformTeacherAccountToDtoRequest(teacherAccount);
    }
}
