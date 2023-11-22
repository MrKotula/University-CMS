package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.TeacherAccountService;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TeacherAccountServiceImpl implements TeacherAccountService {
    private final TeacherAccountRepository teacherAccountRepository;
    private final StudentAccountRepository studentAccountRepository;

    @Override
    public TeacherAccountResponse getTeacherByEmail(String email) {
        TeacherAccount teacherAccount = teacherAccountRepository.getTeacherByEmail(email);

        return TeacherAccountResponse.builder()
                .userId(teacherAccount.getUserId())
                .firstName(teacherAccount.getFirstName())
                .lastName(teacherAccount.getLastName())
                .email(teacherAccount.getEmail())
                .password(teacherAccount.getPassword())
                .passwordCheck(teacherAccount.getPasswordCheck())
                .roles(teacherAccount.getRoles())
                .registrationStatus(teacherAccount.getRegistrationStatus())
                .degree(teacherAccount.getDegree())
                .phoneNumber(teacherAccount.getPhoneNumber())
                .diplomaStudents(teacherAccount.getDiplomaStudents())
                .build();
    }

    @Override
    public void addStudentToScienceSupervisor(String userId, String userIdStudent) {
        TeacherAccount teacherAccount = teacherAccountRepository.findById(userId).get();
        StudentAccount studentAccount = studentAccountRepository.findById(userIdStudent).get();

        List<StudentAccount> listOfStudents = teacherAccount.getDiplomaStudents();

        if(!listOfStudents.contains(studentAccount)){
            teacherAccountRepository.addStudentToScienceSupervisor(userId, userIdStudent);
        }
    }
}
