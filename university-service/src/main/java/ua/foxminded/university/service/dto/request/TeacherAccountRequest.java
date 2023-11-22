package ua.foxminded.university.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.enums.Degree;
import ua.foxminded.university.entity.enums.RegistrationStatus;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"userId", "firstName", "lastName", "email", "degree", "phoneNumber"})
public class TeacherAccountRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordCheck;
    private Set<RoleRequest> roles;
    private RegistrationStatus registrationStatus;
    private Degree degree;
    private String phoneNumber;
    private List<StudentAccountRequest> diplomaStudents;

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ")";
    }
}
