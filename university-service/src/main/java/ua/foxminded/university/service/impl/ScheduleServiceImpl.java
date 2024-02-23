package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Schedule;
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
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.service.dto.response.ScheduleResponse;
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

        scheduleRepository.save(scheduleMapper.transformScheduleFromDto(scheduleRequest));
    }

    @Override
    public ScheduleResponse createSchedule(String teacherId, String groupId, String courseId) {
        return ScheduleResponse.builder()
                .course(courseMapper.transformCourseToDto(courseRepository.findById(courseId).get()))
                .teacher(teacherAccountMapper.transformTeacherAccountToDto(teacherAccountRepository.findById(teacherId).get()))
                .group(groupMapper.transformGroupToDto(groupRepository.findById(groupId).get()))
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
}
