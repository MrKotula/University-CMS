package ua.foxminded.university.controller.moderator;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.ScheduleService;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.request.ScheduleRequestBody;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.validator.exception.ValidationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ModeratorController {
    private static final String GROUP_MODERATOR_API = "/moderator/groups";
    private static final String SCHEDULE_MODERATOR_API = "/moderator/schedule";
    private static final String COURSE_MODERATOR_API = "/moderator/courses";

    private final ScheduleService scheduleService;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TeacherAccountService teacherAccountService;
    private final DateService dateService;

    @GetMapping("/moderator")
    public String openModeratorPage(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/moderatorPage";
    }

    @GetMapping(SCHEDULE_MODERATOR_API)
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

    @PostMapping(value = SCHEDULE_MODERATOR_API, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String actionCreateSchedulePage(@RequestBody ScheduleRequestBody scheduleRequestBody) {
        scheduleService.register(scheduleRequestBody);

        return "redirect:/moderator";
    }

    @GetMapping(SCHEDULE_MODERATOR_API + "/edit")
    public String openEditSchedulePage(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/editSchedulePage";
    }

    @GetMapping(COURSE_MODERATOR_API)
    public String openCoursesPage(Model model) {
        model.addAttribute("dateService", dateService.getCurrentDate());
        model.addAttribute("allCourses", courseService.getAllCourses());

        return "moderator_panel/coursesPage";
    }

    @PostMapping(COURSE_MODERATOR_API)
    public String actionRemoveCourse(@RequestParam String courseId, Model model) {
        try {
            courseService.removeCourse(courseId);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error_panel/errorPage";
        }

        return "redirect:" + COURSE_MODERATOR_API;
    }

    @GetMapping(GROUP_MODERATOR_API)
    public String viewGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/allGroups";
    }

    @GetMapping(GROUP_MODERATOR_API + "/{groupId}")
    public String viewGroupData(@PathVariable String groupId, Model model) {
        model.addAttribute("groupResponse", groupService.getGroupById(groupId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/groupPageModerator";
    }

    @GetMapping(GROUP_MODERATOR_API + "/{groupId}/edit")
    public String editGroupNamePage(@PathVariable String groupId, Model model) {
        model.addAttribute("groupResponse", groupService.getGroupById(groupId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "moderator_panel/groupEditModerator";
    }

    @PostMapping(GROUP_MODERATOR_API + "/{groupId}/edit")
    public String actionEditGroupName(GroupResponse groupResponse, Model model) {
        try {
            groupService.updateGroupName(groupResponse);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error_panel/errorPage";
        }

        return "redirect:" + GROUP_MODERATOR_API;
    }
}
