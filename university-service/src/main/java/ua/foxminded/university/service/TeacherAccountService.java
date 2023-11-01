package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.response.TeacherAccountResponse;

public interface TeacherAccountService {
    TeacherAccountResponse getTeacherByEmail(String email);

    void addStudentToScienceSupervisor(String userId, String userIdStudent);
}
