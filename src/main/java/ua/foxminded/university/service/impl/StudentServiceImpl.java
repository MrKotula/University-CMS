package ua.foxminded.university.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.StudentRepository;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.entity.enums.Status;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentService;
import ua.foxminded.university.validator.UserValidator;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final StudentRepository studentRepository;

    @Override
    public List<Student> findByCourseName(String courseName) {
        return studentRepository.findByCourseName(courseName);
    }

    @Override
    public void addStudentCourse(String studentId, String courseId) {
        studentRepository.addStudentCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {
        studentRepository.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public void deleteById(String id) {
        studentRepository.deleteById(id);
    }

    @Override
    public void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        userValidator.validateData(userRegistrationRequest.getEmail(), userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName());
        userValidator.validatePassword(userRegistrationRequest.getPassword(), userRegistrationRequest.getPasswordCheck());

        Student newStudent = new Student(userRegistrationRequest.getFirstName(), userRegistrationRequest.getLastName(), userRegistrationRequest.getEmail(),
                passwordEncoder.encode(userRegistrationRequest.getPassword()), passwordEncoder.encode(userRegistrationRequest.getPasswordCheck()),
                Status.NEW, RegistrationStatus.NEW);

        studentRepository.save(newStudent);
    }

    @Override
    public void updateEmail(Student student) throws ValidationException {
        userValidator.validateEmail(student.getEmail());

        studentRepository.save(student);
    }

    @Override
    public void updatePassword(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void updateStatus(Status status, String studentId) {
        studentRepository.updateStatus(status, studentId);
    }

    @Override
    public void changeGroup(String groupId, String studentId) {
        studentRepository.changeGroup(groupId, studentId);
    }
}
