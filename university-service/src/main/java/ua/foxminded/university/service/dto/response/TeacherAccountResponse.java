package ua.foxminded.university.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.foxminded.university.entity.Role;
import ua.foxminded.university.entity.StudentAccount;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherAccountResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    @ToString.Exclude
    private String password;
    @ToString.Exclude
    private String passwordCheck;

    private Set<Role> roles;
    private RegistrationStatus registrationStatus;
    private Degree degree;
    private String phoneNumber;
    private List<StudentAccount> diplomaStudents;
}
