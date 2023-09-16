package ua.foxminded.university.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.repository.TeacherAccountRepository;
import ua.foxminded.university.service.TeacherAccountService;
import ua.foxminded.university.service.dto.updateData.TeacherAccountUpdateRequest;

@Service
@AllArgsConstructor
public class TeacherAccountServiceImpl implements TeacherAccountService {
    private final TeacherAccountRepository teacherAccountRepository;

    @Override
    public TeacherAccountUpdateRequest getTeacherByEmail(String email) {
        TeacherAccount teacherAccount = teacherAccountRepository.getTeacherByEmail(email);

        return new TeacherAccountUpdateRequest(teacherAccount.getUserId(), teacherAccount.getFirstName(), teacherAccount.getLastName(),
                teacherAccount.getEmail(), teacherAccount.getPassword(), teacherAccount.getPasswordCheck(), teacherAccount.getRoles(),
                teacherAccount.getRegistrationStatus(), teacherAccount.getDegree(), teacherAccount.getPhoneNumber(), teacherAccount.getDiplomaStudents());
    }
}
