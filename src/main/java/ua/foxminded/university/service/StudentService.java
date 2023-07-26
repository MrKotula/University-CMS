package ua.foxminded.university.service;

import java.util.List;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;

public interface StudentService {   
    void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException;
    
    void updateEmail(Student student) throws ValidationException;
    
    void updatePassword(Student student);
    
    void updateStatus(Status status, String studentId);
    
    void changeGroup(String groupId, String studentId);
    
    List<Student> findByCourseName(String courseName);
    
    void addStudentCourse(String studentId, String courseId);
    
    void removeStudentFromCourse(String studentId, String courseId);

    void deleteById(String id);
}
