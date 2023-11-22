package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import java.util.List;

public interface TeacherAccountService {
    TeacherAccountResponse getTeacherByEmail(String email);

    void addStudentToScienceSupervisor(String userId, String userIdStudent);

    List<TeacherAccountResponse> getAllTeachers();
}
