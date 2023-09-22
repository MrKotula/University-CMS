package ua.foxminded.university.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;

@Service
@AllArgsConstructor
public class StudentAccountServiceImpl implements StudentAccountService {

    private final StudentValidator studentValidator;

    private final PasswordEncoder passwordEncoder;

    private final StudentAccountRepository studentAccountRepository;

    private RoleRepository roleRepository;

    @Override
    public List<StudentAccount> findByCourseName(String courseName) {
        return studentAccountRepository.findByCourseName(courseName);
    }

    @Override
    public void addStudentCourse(String studentId, String courseId) {
        studentValidator.validateStudentId(studentId);
        studentValidator.validateCourseId(courseId);
        studentAccountRepository.addStudentCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {
        studentValidator.validateStudentId(studentId);
        studentValidator.validateCourseId(courseId);
        studentAccountRepository.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public StudentAccountResponse findStudentById(String userId) {
        StudentAccount studentAccount = studentAccountRepository.findById(userId).get();

        return StudentAccountResponse.builder()
                .userId(studentAccount.getUserId())
                .firstName(studentAccount.getFirstName())
                .lastName(studentAccount.getLastName())
                .email(studentAccount.getEmail())
                .password(studentAccount.getPassword())
                .passwordCheck(studentAccount.getPasswordCheck())
                .roles(studentAccount.getRoles())
                .registrationStatus(studentAccount.getRegistrationStatus())
                .groupId(studentAccount.getGroupId())
                .studentCard(studentAccount.getStudentCard())
                .courses(studentAccount.getCourses())
                .build();
    }

    @Override
    public List<StudentAccount> findAllStudents() {
        return studentAccountRepository.findAll();
    }

    @Override
    public void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        studentValidator.validateStudent(userRegistrationRequest);

        Role role = roleRepository.findByRole(RoleModel.STUDENT);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        StudentAccount newStudentAccount = StudentAccount.studentBuilder()
                .firstName(userRegistrationRequest.getFirstName().trim())
                .lastName(userRegistrationRequest.getLastName().trim())
                .email(userRegistrationRequest.getEmail().trim())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword().trim()))
                .passwordCheck(passwordEncoder.encode(userRegistrationRequest.getPasswordCheck().trim()))
                .registrationStatus(RegistrationStatus.NEW)
                .roles(roles)
                .build();

        studentAccountRepository.save(newStudentAccount);
    }

    @Override
    public void updateEmail(StudentAccount studentAccount) throws ValidationException {
        studentValidator.validateStudentId(studentAccount.getUserId());
        studentValidator.validateEmail(studentAccount.getEmail());

        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void updatePassword(StudentAccount studentAccount) {
        studentValidator.validateStudentId(studentAccount.getUserId());
        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void changeGroup(String groupId, String studentId) {
        studentValidator.validateStudentId(studentId);
        studentValidator.validateGroupId(groupId);
        studentAccountRepository.changeGroup(groupId, studentId);
    }
}
