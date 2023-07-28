package ua.foxminded.university.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.enums.RoleModel;
import ua.foxminded.university.registration.UserRegistrationRequest;
import ua.foxminded.university.repository.RoleRepository;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.validator.exception.ValidationException;
import ua.foxminded.university.service.StudentAccountService;
import ua.foxminded.university.validator.UserValidator;

@Service
@AllArgsConstructor
public class StudentAccountServiceImpl implements StudentAccountService {

    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final StudentAccountRepository studentAccountRepository;

    private RoleRepository roleRepository;

    @Override
    public List<StudentAccount> findByCourseName(String courseName) {
        return studentAccountRepository.findByCourseName(courseName);
    }

    @Override
    public void addStudentCourse(String studentId, String courseId) {
        studentAccountRepository.addStudentCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(String studentId, String courseId) {
        studentAccountRepository.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    public void deleteById(String id) {
        studentAccountRepository.deleteById(id);
    }

    @Override
    public void register(UserRegistrationRequest userRegistrationRequest) throws ValidationException {
        userValidator.validateLogin(userRegistrationRequest.getEmail().trim());
        userValidator.validateData(userRegistrationRequest.getEmail().trim(), userRegistrationRequest.getFirstName().trim(), userRegistrationRequest.getLastName().trim());
        userValidator.validatePassword(userRegistrationRequest.getPassword().trim(), userRegistrationRequest.getPasswordCheck().trim());

        Role role = roleRepository.findByRole(RoleModel.STUDENT);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        StudentAccount newStudentAccount = new StudentAccount(userRegistrationRequest.getFirstName().trim(), userRegistrationRequest.getLastName().trim(),
                userRegistrationRequest.getEmail().trim(), passwordEncoder.encode(userRegistrationRequest.getPassword().trim()),
                passwordEncoder.encode(userRegistrationRequest.getPasswordCheck().trim()), RegistrationStatus.NEW, roles);

        studentAccountRepository.save(newStudentAccount);
    }

    @Override
    public void updateEmail(StudentAccount studentAccount) throws ValidationException {
        userValidator.validateEmail(studentAccount.getEmail());

        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void updatePassword(StudentAccount studentAccount) {
        studentAccountRepository.save(studentAccount);
    }

    @Override
    public void changeGroup(String groupId, String studentId) {
        studentAccountRepository.changeGroup(groupId, studentId);
    }
}
