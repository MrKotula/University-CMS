package ua.foxminded.university.controller.admin;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.foxminded.university.service.DateService;
import ua.foxminded.university.service.dto.dataupdate.UserAccountUpdateRequest;
import ua.foxminded.university.service.RoleService;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.UserAccountService;

@Controller
@AllArgsConstructor
@Log4j2
public class AdminController {

    private final UserAccountService userAccountService;
    private final CourseService courseService;
    private final RoleService roleService;
    private final DateService dateService;

    @GetMapping("/admin")
    public String openAdminPanel(Model model) {
        model.addAttribute("title", "Admin panel");
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/admin";
    }

    @GetMapping("/admin/users/all")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userAccountService.findAllUsers());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/allUsers";
    }

    @GetMapping("/admin/user/{userId}")
    public String viewUserData(@PathVariable String userId, Model model) {
        model.addAttribute("userAccount", userAccountService.findUserById(userId));
        model.addAttribute("courses", courseService.findByStudentId(userId));
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userPageAdmin";
    }

    @GetMapping("/admin/user/editData/{userId}")
    public String changeUserData(@ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, Model model) {
        model.addAttribute("userUpdateRequest", userAccountUpdateRequest);
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userEditAdmin";
    }

    @PostMapping("/admin/user/editData/{userId}")
    public String editUserData(@ModelAttribute UserAccountUpdateRequest userAccountUpdateRequest, Model model) {
        userAccountService.updateUserData(userAccountUpdateRequest);
        log.warn("Changed data for user by help Admin! " + userAccountUpdateRequest.toString());

        return "redirect:/admin/user/"+ userAccountUpdateRequest.getUserId();
    }

    @GetMapping("/admin/user/editRole/{userId}")
    public String changeUserRole(@PathVariable String userId, Model model) {
        model.addAttribute("updateUserAccountRequest", userAccountService.findUserById(userId));
        model.addAttribute("listRoles", roleService.findAllRoles());
        model.addAttribute("dateService", dateService.getCurrentDate());

        return "adminPanel/userEditRoleAdmin";
    }

    @PostMapping("/admin/user/editRole/{userId}")
    public String updateUserRole(UserAccountUpdateRequest userAccountUpdateRequest, @RequestParam(defaultValue = "USER") String roles) {
        userAccountService.updateUserRoles(userAccountUpdateRequest, roles);
        log.warn("Changed roles for user by help Admin! UserId: " + userAccountUpdateRequest.getUserId());

        return "redirect:/admin/user/"+ userAccountUpdateRequest.getUserId();
    }
}
