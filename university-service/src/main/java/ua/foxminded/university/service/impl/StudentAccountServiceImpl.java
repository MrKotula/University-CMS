package ua.foxminded.university.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.service.dto.registration.UserRegistrationRequest;
import ua.foxminded.university.service.dto.request.StudentAccountRequest;
import ua.foxminded.university.service.dto.response.StudentAccountResponse;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.service.mapper.StudentAccountMapper;
import ua.foxminded.university.validator.StudentValidator;
import ua.foxminded.university.validator.exception.ValidationException;

@Service
@Transactional
@AllArgsConstructor
public class StudentAccountServiceImpl implements StudentAccountService {
    private final StudentValidator studentValidator;
    private final PasswordEncoder passwordEncoder;
    private final StudentAccountRepository studentAccountRepository;
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;

    private final StudentAccountMapper studentAccountMapper;

    @Override
    public List<StudentAccountResponse> findByCourseName(String courseName) {
        List<StudentAccount> studentAccountList = studentAccountRepository.findByCourseName(courseName);

        return studentAccountMapper.transformListStudentsToDto(studentAccountList);
    }

    @Override
    public void addStudentCourse(StudentAccountRequest studentAccountRequest, String courseId) {
        studentValidator.validateStudentId(studentAccountRequest.getUserId());
        studentValidator.validateCourseId(courseId);
        studentValidator.validateMaxAvailableCourses(studentAccountRequest);

        if(studentValidator.validateAvailableCourses(studentAccountRequest, courseId)) {
            studentAccountRepository.addStudentCourse(studentAccountRequest.getUserId(), courseId);
        }
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

        return studentAccountMapper.transformStudentAccountToDto(studentAccount);
    }

    @Override
    public List<StudentAccountResponse> findAllStudents() {
        List<StudentAccount> studentAccountList = studentAccountRepository.findAll();

        return studentAccountMapper.transformListStudentsToDto(studentAccountList);
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
    public void updateEmail(StudentAccountRequest studentAccountRequest) throws ValidationException {
        studentValidator.validateStudentId(studentAccountRequest.getUserId());
        studentValidator.validateEmail(studentAccountRequest.getEmail());

        StudentAccount studentAccount = studentAccountMapper.transformStudentAccountFromDto(studentAccountRequest);

        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void updatePassword(StudentAccountRequest studentAccountRequest) {
        studentValidator.validateStudentId(studentAccountRequest.getUserId());

        StudentAccount studentAccount = studentAccountMapper.transformStudentAccountFromDto(studentAccountRequest);

        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void changeGroup(String groupId, String studentId) {
        studentValidator.validateStudentId(studentId);
        studentValidator.validateGroupId(groupId);
        studentAccountRepository.changeGroup(groupId, studentId);
    }

    @Override
    public StudentAccountResponse getStudentByEmail(String email) {
        return studentAccountMapper.transformStudentAccountToDto(studentAccountRepository.getStudentByEmail(email));
    }
}
