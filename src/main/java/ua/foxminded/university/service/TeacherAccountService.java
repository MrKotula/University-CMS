package ua.foxminded.university.service;

import ua.foxminded.university.service.dto.updateData.TeacherAccountUpdateRequest;

public interface TeacherAccountService {
    TeacherAccountUpdateRequest getTeacherByEmail(String email);
}
