package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentAccountService {
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;

    void updateEmail(StudentAccount studentAccount) throws ValidationException;

    void updatePassword(StudentAccount studentAccount);

    void changeGroup(String groupId, String studentId);

    List<StudentAccount> findByCourseName(String courseName);

    void addStudentCourse(String studentId, String courseId);

    void removeStudentFromCourse(String studentId, String courseId);

    StudentAccountResponse findStudentById(String userId);

    List<StudentAccount> findAllStudents();
}
