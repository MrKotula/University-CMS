package ua.foxminded.university.controller.moderator;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.request.ScheduleRequest;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.validator.ScheduleValidator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ModeratorController {

    private final ScheduleService scheduleService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TeacherAccountService teacherAccountService;
    private final DateService dateService;
    private final ScheduleValidator scheduleValidator;

    @GetMapping("/user/moderator")
    public String openModeratorPage(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/moderatorPage";
    }

    @GetMapping("/user/moderator/schedule")
    public String openCreateSchedulePage(Model model) {
        LocalDate currentDate = dateService.getCurrentDate();

        List<LocalTime> startOfLecture = scheduleService.getListLectureStartTimes();
        List<LocalTime> endOfLecture = scheduleService.getListLectureEndTimes();
        List<String> lectureRooms = scheduleService.getListLectureRooms();

        model.addAttribute("dateService", currentDate);
        model.addAttribute("teachers", teacherAccountService.getAllTeachers());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("startOfLecture", startOfLecture);
        model.addAttribute("listOfNextTwoWeeks", dateService.getNearNextTwoWeeks());
        model.addAttribute("endOfLecture", endOfLecture);
        model.addAttribute("lectureRooms", lectureRooms);

        return "moderator_panel/createSchedulePage";
    }

    @PostMapping(value = "/user/moderator/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String actionCreateSchedulePage(@RequestBody ScheduleRequestBody scheduleRequestBody) {
        ScheduleRequest scheduleRequest = scheduleService.getPreparedScheduleRequest(scheduleRequestBody);

        scheduleValidator.checkAvailableLectorRoom(scheduleRequest);
        scheduleValidator.checkAvailableTeacher(scheduleRequest);
        scheduleValidator.checkAvailableGroup(scheduleRequest);

        scheduleService.register(scheduleRequest);

        return "moderator_panel/createSchedulePage";
    }

    @GetMapping("/user/moderator/schedule/edit")
    public String openEditSchedulePage(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/editSchedulePage";
    }
}
