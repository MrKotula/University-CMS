package ua.foxminded.university.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import ua.foxminded.university.service.TeacherAccountService;

@Controller
@AllArgsConstructor
@Log4j2
public class TeacherController {
    private final TeacherAccountService teacherAccountService;
}
