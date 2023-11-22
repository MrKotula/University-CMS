package ua.foxminded.university.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.repository.StudentAccountRepository;
import ua.foxminded.university.service.dto.response.TeacherAccountResponse;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.TeacherAccount;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import ua.foxminded.university.repository.TeacherAccountRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherAccountServiceImplTest {

    @Mock
    private TeacherAccountRepository teacherAccountRepository;

    @Mock
    private StudentAccountRepository studentAccountRepository;

    @InjectMocks
    private TeacherAccountServiceImpl teacherAccountService;

    private TeacherAccount teacherAccountTest = TeacherAccount.teacherBuilder()
            .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
            .firstName("Jin")
            .lastName("Tores")
            .email("teacherMail@gmail.com")
            .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
            .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
            .roles(new HashSet<>())
            .registrationStatus(RegistrationStatus.REGISTERED)
            .degree(Degree.DOCTORAL)
            .phoneNumber("067-768-874")
            .diplomaStudents(new ArrayList<>())
            .build();

    private StudentAccount testStudentAccount = new StudentAccount("33c99439-aaf0-4ebd-a07a-bd0c550d2311", "Jane", "Does", "dtestMail@gmail.com", null, null,
            RegistrationStatus.NEW, new HashSet<>(),"3c01e6f1-762e-43b8-a6e1-7cf493ce5325", "RT85796142");

    @Test
    void verifyUseMethodGetTeacherByEmailTest() {
        TeacherAccountResponse teacherAccountResponseTest = TeacherAccountResponse.builder()
                .userId("33c99439-aaf0-4ebd-a07a-bd0c550d8799")
                .firstName("Jin")
                .lastName("Tores")
                .email("teacherMail@gmail.com")
                .password("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .passwordCheck("$2a$10$nWD4aCZMQydDrZjAFYFwOOa7lO3cuI6b/el3ZubPoCmHQnu6YrTMS")
                .roles(new HashSet<>())
                .registrationStatus(RegistrationStatus.REGISTERED)
                .degree(Degree.DOCTORAL)
                .phoneNumber("067-768-874")
                .diplomaStudents(new ArrayList<>())
                .build();

        when(teacherAccountRepository.getTeacherByEmail("teacherMail@gmail.com")).thenReturn(teacherAccountTest);

        assertEquals(teacherAccountResponseTest, teacherAccountService.getTeacherByEmail("teacherMail@gmail.com"));
    }

    @Test
    void verifyUseAddStudentToScienceSupervisorWhenStudentAlreadyIsTest() {
        ArrayList listOfStudents = mock(ArrayList.class);

        listOfStudents.add(testStudentAccount);

        teacherAccountTest.setDiplomaStudents(listOfStudents);

        when(teacherAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d8799")).thenReturn(Optional.ofNullable(teacherAccountTest));
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311")).thenReturn(Optional.ofNullable(testStudentAccount));
        doReturn(true).when(listOfStudents).contains(testStudentAccount);

        teacherAccountService.addStudentToScienceSupervisor(teacherAccountTest.getUserId(), testStudentAccount.getUserId());

        verify(teacherAccountRepository).findById(teacherAccountTest.getUserId());
        verify(studentAccountRepository).findById(testStudentAccount.getUserId());
    }

    @Test
    void verifyUseAddStudentToScienceSupervisorWhenAddStudentToSupervisorTest() {
        ArrayList listOfStudents = mock(ArrayList.class);

        listOfStudents.add(testStudentAccount);

        teacherAccountTest.setDiplomaStudents(listOfStudents);

        when(teacherAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d8799")).thenReturn(Optional.ofNullable(teacherAccountTest));
        when(studentAccountRepository.findById("33c99439-aaf0-4ebd-a07a-bd0c550d2311")).thenReturn(Optional.ofNullable(testStudentAccount));
        doReturn(false).when(listOfStudents).contains(testStudentAccount);

        teacherAccountService.addStudentToScienceSupervisor(teacherAccountTest.getUserId(), testStudentAccount.getUserId());

        verify(teacherAccountRepository).findById(teacherAccountTest.getUserId());
        verify(studentAccountRepository).findById(testStudentAccount.getUserId());
        verify(teacherAccountRepository).addStudentToScienceSupervisor(teacherAccountTest.getUserId(), testStudentAccount.getUserId());
    }
}
