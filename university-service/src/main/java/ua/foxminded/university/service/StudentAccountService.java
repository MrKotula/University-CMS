package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentAccountService {
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;

    void updateEmail(StudentAccountRequest studentAccountRequest) throws ValidationException;

    void updatePassword(StudentAccountRequest studentAccountRequest);

    void changeGroup(String groupId, String studentId);

    List<StudentAccountResponse> findByCourseName(String courseName);

    void addStudentCourse(StudentAccountRequest studentAccountRequest, String courseId);

    void removeStudentFromCourse(String studentId, String courseId);

    StudentAccountResponse findStudentById(String userId);

    List<StudentAccountResponse> findAllStudents();

    StudentAccountResponse getStudentByEmail(String email);

    void updateStudentData(StudentAccountResponse studentAccountDtoRequest);
}
