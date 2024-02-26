package ua.foxminded.university.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.RoleService;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.UserAccountService;
import ua.foxminded.university.service.dto.response.GroupResponse;
import ua.foxminded.university.validator.exception.ValidationException;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {
    private static final String ERROR_MESSAGE_VIOLATES_SCHEDULE = "This group have a schedule. Remove schedule before remove group!";

    private final UserAccountService userAccountService;
    private final CourseService courseService;
    private final RoleService roleService;
    private final DateService dateService;
    private final GroupService groupService;

    @GetMapping("/admin")
    public String openAdminPanel(Model model) {
        model.addAttribute("title", "Admin panel");
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/admin";
    }

    @GetMapping("/admin/users")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userAccountService.findAllUsers());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/allUsers";
    }

    @GetMapping("/admin/users/{userId}")
    public String viewUserData(@PathVariable String userId, Model model) {
        model.addAttribute("userAccount", userAccountService.findUserById(userId));
        model.addAttribute("courses", courseService.findByStudentId(userId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userPageAdmin";
    }

    @GetMapping("/admin/users/{userId}/edit")
    public String changeUserData(@ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, Model model) {
        model.addAttribute("userUpdateRequest", userAccountUpdateRequest);
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userEditAdmin";
    }

    @PostMapping("/admin/users/{userId}/edit")
    public String editUserData(@ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, Model model) {
        userAccountService.updateUserData(userAccountUpdateRequest);
        log.warn("Changed data for user by help Admin! " + userAccountUpdateRequest.toString());

        return "redirect:/admin/users/"+ userAccountUpdateRequest.getUserId();
    }

    @GetMapping("/admin/users/{userId}/roles")
    public String changeUserRole(@PathVariable String userId, Model model) {
        model.addAttribute("updateUserAccountRequest", userAccountService.findUserById(userId));
        model.addAttribute("listRoles", roleService.findAllRoles());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userEditRoleAdmin";
    }

    @PostMapping("/admin/users/{userId}/roles")
    public String updateUserRole(UserAccountUpdateRequest userAccountUpdateRequest, @RequestParam(defaultValue = "USER") String roles) {
        userAccountService.updateUserRoles(userAccountUpdateRequest, roles);
        log.warn("Changed roles for user by help Admin! UserId: " + userAccountUpdateRequest.getUserId());

        return "redirect:/admin/users/"+ userAccountUpdateRequest.getUserId();
    }

    @GetMapping("/admin/groups")
    public String viewGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/allGroups";
    }

    @GetMapping("/admin/groups/{groupId}")
    public String viewGroupData(@PathVariable String groupId, Model model) {
        model.addAttribute("groupResponse", groupService.getGroupById(groupId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/groupPageAdmin";
    }

    @DeleteMapping("/admin/groups/{groupId}")
    public String actionRemoveGroup(@PathVariable String groupId, Model model) {
        try {
            groupService.removeGroup(groupId);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error_panel/errorPage";

        } catch (DataIntegrityViolationException e) {
            model.addAttribute("dataIntegrityErrorMessage", ERROR_MESSAGE_VIOLATES_SCHEDULE);

            return "error_panel/errorPage";
        }

        return "redirect:/admin/groups";
    }

    @GetMapping("/admin/groups/{groupId}/edit")
    public String editGroupNamePage(@PathVariable String groupId, Model model) {
        model.addAttribute("groupResponse", groupService.getGroupById(groupId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/groupEditAdmin";
    }

    @PostMapping("/admin/groups/{groupId}/edit")
    public String actionEditGroupName(GroupResponse groupResponse, Model model) {
        try {
            groupService.updateGroupName(groupResponse);
        } catch (ValidationException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "error_panel/errorPage";
        }

        return "redirect:/admin/groups";
    }
}
